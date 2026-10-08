package org.exlp.maven.reachability;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContextFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.exlp.maven.reachability.fixture.AnnotatedClass;

public abstract class AbstractReachabilityTest
{
	protected static final ObjectMapper MAPPER = new ObjectMapper();

	protected File testClassesDirectory() throws Exception
	{
		return classpathElement(AnnotatedClass.class);
	}

	/**
	 * Classpath of the scanned module: its output directory and its dependencies. The JAXB API jar
	 * carries the annotation types of the fixtures, which is part of the module's classpath.
	 */
	protected List<String> moduleClasspath() throws Exception
	{
		return classpath(testClassesDirectory(), classpathElement(JAXBContextFactory.class));
	}

	private File classpathElement(Class<?> type) throws Exception
	{
		return new File(type.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsoluteFile();
	}

	protected List<String> classpath(File... elements)
	{
		List<String> classpath = new ArrayList<String>();
		for(File element : elements) {classpath.add(element.getAbsolutePath());}
		return classpath;
	}

	/** Fully qualified names of the annotation types, as they are configured in the plugin. */
	protected List<String> annotationTypes(Class<?>... types)
	{
		List<String> names = new ArrayList<String>();
		for(Class<?> type : types) {names.add(type.getName());}
		return names;
	}

	protected Set<String> types(ReachabilityMetadata metadata)
	{
		Set<String> types = new TreeSet<String>();
		for(ReflectionRegistration registration : metadata.getReflection()) {types.add(registration.getType());}
		return types;
	}

	protected ReflectionRegistration registration(ReachabilityMetadata metadata, String type)
	{
		for(ReflectionRegistration registration : metadata.getReflection())
		{
			if(registration.getType().equals(type)) {return registration;}
		}
		return null;
	}

	protected JsonNode json(File file) throws IOException
	{
		return MAPPER.readTree(file);
	}
}
