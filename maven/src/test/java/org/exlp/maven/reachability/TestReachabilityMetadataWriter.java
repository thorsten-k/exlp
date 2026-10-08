package org.exlp.maven.reachability;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class TestReachabilityMetadataWriter extends AbstractReachabilityTest
{
	private ReachabilityMetadataWriter writer;
	private File directory;

	@BeforeEach
	public void init() throws Exception
	{
		writer = new ReachabilityMetadataWriter();
		directory = new File(testClassesDirectory().getParentFile(), "reachability-writer");
	}

	@Test
	public void composesArtifactPath() throws Exception
	{
		File file = writer.outputFile(directory, "net.sf.exlp", "exlp-core");
		String path = file.getPath().replace('\\', '/');

		assertTrue(path.endsWith("META-INF/native-image/net.sf.exlp/exlp-core/reachability-metadata.json"));
	}

	@Test
	public void replacesPriorMetadata() throws Exception
	{
		File file = writer.outputFile(directory, "net.sf.exlp", "exlp-core");

		writer.write(metadata("com.example.First"), file);
		assertEquals("com.example.First", json(file).get("reflection").get(0).get("type").asText());

		writer.write(metadata("com.example.Second"), file);
		JsonNode root = json(file);

		assertEquals(1, root.get("reflection").size());
		assertEquals("com.example.Second", root.get("reflection").get(0).get("type").asText());
		assertTrue(root.toString().indexOf("com.example.First")<0);
	}

	@Test
	public void writesValidMetadataWithoutRegistrations() throws Exception
	{
		File file = writer.outputFile(directory, "net.sf.exlp", "exlp-core");
		writer.write(new ReachabilityMetadata(), file);

		JsonNode root = json(file);
		assertTrue(root.get("reflection").isArray());
		assertEquals(0, root.get("reflection").size());
	}

	private ReachabilityMetadata metadata(String type)
	{
		return new ReachabilityMetadata(Arrays.asList(new ReflectionRegistration(type)));
	}
}
