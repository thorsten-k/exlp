package org.exlp.maven.reachability;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

/**
 * Scans the production classes of the current module and registers every class that bears one of the
 * configured annotation types. The annotation types are resolved from the classpath of the module, so
 * annotations of dependencies are supported as well.
 */
public class ReachabilityMetadataGenerator
{
	public ReachabilityMetadata generate(List<String> classpathElements, File productionDirectory, List<String> annotationTypes) throws IOException
	{
		File production = productionDirectory.getCanonicalFile();
		Map<String,ReflectionRegistration> registrations = new TreeMap<String,ReflectionRegistration>();

		try (ScanResult scanResult = new ClassGraph()
				.enableClassInfo()
				.enableAnnotationInfo()
				.overrideClasspath(classpathElements)
				.scan())
		{
			for(ClassInfo classInfo : scanResult.getAllClasses())
			{
				if(!isProductionClass(classInfo, production)) {continue;}
				if(!isSelected(classInfo, annotationTypes)) {continue;}

				registrations.put(classInfo.getName(), new ReflectionRegistration(classInfo.getName()));
			}
		}
		return new ReachabilityMetadata(registrations.values());
	}

	private boolean isSelected(ClassInfo classInfo, List<String> annotationTypes)
	{
		for(String annotationType : annotationTypes)
		{
			if(classInfo.getAnnotationInfo(annotationType)!=null) {return true;}
		}
		return false;
	}

	private boolean isProductionClass(ClassInfo classInfo, File production) throws IOException
	{
		File element = classInfo.getClasspathElementFile();
		if(element==null) {return false;}
		return element.getCanonicalFile().equals(production);
	}
}
