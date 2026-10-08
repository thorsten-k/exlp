package org.exlp.maven.reachability;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.databind.JsonNode;

import org.exlp.maven.reachability.fixture.AnnotatedClass;
import org.exlp.maven.reachability.fixture.DoublyAnnotated;
import org.exlp.maven.reachability.fixture.InheritedBase;
import org.exlp.maven.reachability.fixture.InheritedSubclass;
import org.exlp.maven.reachability.fixture.InterfaceImplementation;
import org.exlp.maven.reachability.fixture.MarkedInterface;
import org.exlp.maven.reachability.fixture.NonPublicMembers;
import org.exlp.maven.reachability.fixture.PlainBase;
import org.exlp.maven.reachability.fixture.PlainSubclass;
import org.exlp.maven.reachability.fixture.Unannotated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestReachabilityMetadataGenerator extends AbstractReachabilityTest
{
	private static final Set<String> REFLECTION_FIELDS = new TreeSet<String>(Arrays.asList("type","allDeclaredConstructors","allDeclaredFields","allDeclaredMethods"));

	private ReachabilityMetadataGenerator generator;
	private File testClasses;

	@BeforeEach
	public void init() throws Exception
	{
		generator = new ReachabilityMetadataGenerator();
		testClasses = testClassesDirectory();
	}

	@Test
	public void selectsClassesWithConfiguredAnnotation() throws Exception
	{
		ReachabilityMetadata metadata = generate(XmlAccessorType.class);
		Set<String> types = types(metadata);

		assertTrue(types.contains(AnnotatedClass.class.getName()));
		assertTrue(types.contains(InheritedBase.class.getName()));
		assertTrue(types.contains(InheritedSubclass.class.getName()));
		assertFalse(types.contains(Unannotated.class.getName()));
		assertFalse(types.contains(PlainBase.class.getName()));
		assertFalse(types.contains(PlainSubclass.class.getName()));
	}

	@Test
	public void excludesClassesOutsideProductionDirectory() throws Exception
	{
		File other = new File(testClasses.getParentFile(), "classes");
		ReachabilityMetadata metadata = generator.generate(moduleClasspath(), other, annotationTypes(XmlAccessorType.class));

		assertTrue(types(metadata).isEmpty());
	}

	@Test
	public void registersAllDeclaredMembersOfSelectedClasses() throws Exception
	{
		ReachabilityMetadata metadata = generate(XmlAccessorType.class);
		ReflectionRegistration registration = registration(metadata, NonPublicMembers.class.getName());

		assertNotNull(registration);
		assertTrue(registration.isAllDeclaredConstructors());
		assertTrue(registration.isAllDeclaredFields());
		assertTrue(registration.isAllDeclaredMethods());
	}

	@Test
	public void combinesRegistrationsOfMultipleAnnotations() throws Exception
	{
		ReachabilityMetadata metadata = generate(XmlAccessorType.class, XmlTransient.class);

		int entries = 0;
		for(ReflectionRegistration registration : metadata.getReflection())
		{
			if(registration.getType().equals(DoublyAnnotated.class.getName())) {entries++;}
		}

		assertEquals(1, entries);
		ReflectionRegistration registration = registration(metadata, DoublyAnnotated.class.getName());
		assertTrue(registration.isAllDeclaredConstructors());
		assertTrue(registration.isAllDeclaredFields());
		assertTrue(registration.isAllDeclaredMethods());
	}

	@Test
	public void appliesInheritedAnnotationSemantics() throws Exception
	{
		ReachabilityMetadata metadata = generate(XmlAccessorType.class, XmlTransient.class);
		Set<String> types = types(metadata);

		assertTrue(types.contains(InheritedSubclass.class.getName()));
		assertFalse(types.contains(PlainSubclass.class.getName()));
		assertFalse(types.contains(InterfaceImplementation.class.getName()));
		assertTrue(types.contains(MarkedInterface.class.getName()));
	}

	@Test
	public void generatesReflectionRegistrationsOnly() throws Exception
	{
		ReachabilityMetadata metadata = generate(XmlAccessorType.class);
		JsonNode root = MAPPER.readTree(MAPPER.writeValueAsString(metadata));

		assertEquals(1, root.size());
		assertTrue(root.has("reflection"));
		for(JsonNode entry : root.get("reflection"))
		{
			Set<String> properties = new TreeSet<String>();
			entry.fieldNames().forEachRemaining(property -> properties.add(property));
			assertTrue(REFLECTION_FIELDS.containsAll(properties));
			assertTrue(properties.contains("type"));
		}
	}

	@Test
	public void producesEmptyMetadataWithoutMatches() throws Exception
	{
		ReachabilityMetadata metadata = generate(XmlRegistry.class);

		assertTrue(metadata.getReflection().isEmpty());

		JsonNode root = MAPPER.readTree(MAPPER.writeValueAsString(metadata));
		assertTrue(root.get("reflection").isArray());
		assertEquals(0, root.get("reflection").size());
	}

	private ReachabilityMetadata generate(Class<?>... annotationTypes) throws Exception
	{
		return generator.generate(moduleClasspath(), testClasses, annotationTypes(annotationTypes));
	}
}
