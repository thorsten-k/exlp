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

	protected AnnotationRegistration annotation(Class<?> type)
	{
		return annotation(type.getName(), true, true, true, true);
	}

	protected AnnotationRegistration annotation(Class<?> type, boolean registerClass, boolean constructors, boolean fields, boolean methods)
	{
		return annotation(type.getName(), registerClass, constructors, fields, methods);
	}

	protected AnnotationRegistration annotation(String type, boolean registerClass, boolean constructors, boolean fields, boolean methods)
	{
		AnnotationRegistration annotation = new AnnotationRegistration();
		annotation.setType(type);
		annotation.setRegisterClass(registerClass);
		annotation.setRegisterConstructors(constructors);
		annotation.setRegisterFields(fields);
		annotation.setRegisterMethods(methods);
		return annotation;
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
