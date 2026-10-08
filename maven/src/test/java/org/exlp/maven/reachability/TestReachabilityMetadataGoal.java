package org.exlp.maven.reachability;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.maven.plugin.MojoExecutionException;
import org.exlp.maven.goal.ReachabilityMetadataGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.exlp.maven.reachability.fixture.AnnotatedClass;

public class TestReachabilityMetadataGoal extends AbstractReachabilityTest
{
	private ReachabilityMetadataGoal goal;
	private ReachabilityMetadataWriter writer;
	private File testClasses;

	@BeforeEach
	public void init() throws Exception
	{
		testClasses = testClassesDirectory();

		writer = new ReachabilityMetadataWriter();
		goal = new ReachabilityMetadataGoal();
		goal.setGroupId("net.sf.exlp");
		goal.setArtifactId("exlp-maven");
		goal.setClasspathElements(moduleClasspath());
		goal.setOutputDirectory(testClasses);
		goal.setAnnotations(annotationTypes(XmlAccessorType.class));
	}

	@Test
	public void writesMetadataAtArtifactPath() throws Exception
	{
		goal.execute();

		File file = writer.outputFile(testClasses, "net.sf.exlp", "exlp-maven");
		assertTrue(file.exists());
		assertTrue(file.getPath().startsWith(testClasses.getPath()));

		Set<String> types = types(MAPPER.readValue(file, ReachabilityMetadata.class));
		assertTrue(types.contains(AnnotatedClass.class.getName()));
	}

	@Test
	public void producesIdenticalRegistrationsOnEachRun() throws Exception
	{
		File file = writer.outputFile(testClasses, "net.sf.exlp", "exlp-maven");

		goal.execute();
		String first = content(file);

		goal.execute();
		assertEquals(first, content(file));
	}

	@Test
	public void failsWithoutConfiguredAnnotations()
	{
		goal.setAnnotations(null);

		MojoExecutionException e = assertThrows(MojoExecutionException.class, () -> goal.execute());
		assertTrue(e.getMessage().contains("annotations"));
	}

	@Test
	public void failsWithoutAnnotationType()
	{
		goal.setAnnotations(Arrays.asList(""));

		MojoExecutionException e = assertThrows(MojoExecutionException.class, () -> goal.execute());
		assertTrue(e.getMessage().contains("annotation type"));
	}

	@Test
	public void failsWithoutClasspath()
	{
		goal.setClasspathElements(null);

		MojoExecutionException e = assertThrows(MojoExecutionException.class, () -> goal.execute());
		assertTrue(e.getMessage().contains("classpath"));
	}

	@Test
	public void failsWhenMetadataCannotBeWritten() throws Exception
	{
		File blocked = new File(testClasses.getParentFile(), "reachability-goal/blocked");
		blocked.getParentFile().mkdirs();
		Files.write(blocked.toPath(), "not a directory".getBytes(StandardCharsets.UTF_8));
		goal.setOutputDirectory(blocked);

		MojoExecutionException e = assertThrows(MojoExecutionException.class, () -> goal.execute());
		assertTrue(e.getMessage().contains("failed"));
	}

	private String content(File file) throws Exception
	{
		return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
	}
}
