package org.exlp.maven.goal;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.exlp.maven.reachability.ReachabilityMetadata;
import org.exlp.maven.reachability.ReachabilityMetadataGenerator;
import org.exlp.maven.reachability.ReachabilityMetadataWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name="reachabilityMetadata", requiresDependencyResolution=ResolutionScope.COMPILE)
public class ReachabilityMetadataGoal extends AbstractMojo
{
	final static Logger logger = LoggerFactory.getLogger(ReachabilityMetadataGoal.class);

	@Parameter(defaultValue="${project.groupId}", readonly=true, required=true) private String groupId;
	@Parameter(defaultValue="${project.artifactId}", readonly=true, required=true) private String artifactId;
	@Parameter(defaultValue="${project.build.outputDirectory}", readonly=true, required=true) private File outputDirectory;
	@Parameter(defaultValue="${project.compileClasspathElements}", readonly=true, required=true) private List<String> classpathElements;
	@Parameter private List<String> annotations;

	public void execute() throws MojoExecutionException
	{
		checkConfiguration();

		ReachabilityMetadataGenerator generator = new ReachabilityMetadataGenerator();
		ReachabilityMetadataWriter writer = new ReachabilityMetadataWriter();

		try
		{
			ReachabilityMetadata metadata = generator.generate(classpathElements, outputDirectory, annotations);
			File file = writer.outputFile(outputDirectory, groupId, artifactId);
			writer.write(metadata, file);
			logger.info("Generated "+metadata.getReflection().size()+" reflection registrations in "+file.getAbsolutePath());
		}
		catch (Exception e)
		{
			throw new MojoExecutionException("Reachability metadata generation failed: "+e.getMessage(), e);
		}
	}

	private void checkConfiguration() throws MojoExecutionException
	{
		if(annotations==null || annotations.isEmpty())
		{
			throw new MojoExecutionException("Configuration error: no annotation type is configured (parameter 'annotations')");
		}
		for(String annotation : annotations)
		{
			if(annotation==null || annotation.trim().isEmpty())
			{
				throw new MojoExecutionException("Configuration error: an annotation type is missing (parameter 'annotations')");
			}
		}
		if(outputDirectory==null)
		{
			throw new MojoExecutionException("Configuration error: the build output directory is not set");
		}
		if(classpathElements==null || classpathElements.isEmpty())
		{
			throw new MojoExecutionException("Configuration error: the compile classpath of the module is empty");
		}
		if(groupId==null || groupId.trim().isEmpty())
		{
			throw new MojoExecutionException("Configuration error: the groupId of the module is not set");
		}
		if(artifactId==null || artifactId.trim().isEmpty())
		{
			throw new MojoExecutionException("Configuration error: the artifactId of the module is not set");
		}
	}

	public void setGroupId(String groupId) {this.groupId = groupId;}
	public void setArtifactId(String artifactId) {this.artifactId = artifactId;}
	public void setOutputDirectory(File outputDirectory) {this.outputDirectory = outputDirectory;}
	public void setClasspathElements(List<String> classpathElements) {this.classpathElements = classpathElements;}
	public void setAnnotations(List<String> annotations) {this.annotations = annotations;}
}
