package org.exlp.maven.reachability;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

/**
 * Scans the production classes of the current module and combines the configured annotations per class.
 * The annotation types are resolved from the classpath of the module, so annotations of dependencies are
 * supported as well.
 */
public class ReachabilityMetadataGenerator
{
	public ReachabilityMetadata generate(List<String> classpathElements, File productionDirectory, List<AnnotationRegistration> annotations) throws IOException
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

				for(AnnotationRegistration annotation : annotations)
				{
					if(classInfo.getAnnotationInfo(annotation.getType())==null) {continue;}

					ReflectionRegistration registration = registrations.get(classInfo.getName());
					if(registration==null)
					{
						registration = new ReflectionRegistration(classInfo.getName());
						registrations.put(classInfo.getName(), registration);
					}
					registration.apply(annotation);
				}
			}
		}
		return new ReachabilityMetadata(selected(registrations.values()));
	}

	private List<ReflectionRegistration> selected(Collection<ReflectionRegistration> registrations)
	{
		List<ReflectionRegistration> result = new ArrayList<ReflectionRegistration>();
		for(ReflectionRegistration registration : registrations)
		{
			if(registration.isRegistered()) {result.add(registration);}
		}
		return result;
	}

	private boolean isProductionClass(ClassInfo classInfo, File production) throws IOException
	{
		File element = classInfo.getClasspathElementFile();
		if(element==null) {return false;}
		return element.getCanonicalFile().equals(production);
	}
}
