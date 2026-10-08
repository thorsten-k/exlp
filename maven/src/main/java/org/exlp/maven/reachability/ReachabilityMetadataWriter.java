package org.exlp.maven.reachability;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Writes the reachability metadata to the artifact path of the current module, replacing prior metadata.
 */
public class ReachabilityMetadataWriter
{
	public static final String DIRECTORY_NATIVE_IMAGE = "META-INF/native-image";
	public static final String FILE_NAME = "reachability-metadata.json";

	public File outputFile(File outputDirectory, String groupId, String artifactId)
	{
		String path = DIRECTORY_NATIVE_IMAGE+"/"+groupId+"/"+artifactId+"/"+FILE_NAME;
		return new File(outputDirectory, path);
	}

	public void write(ReachabilityMetadata metadata, File file) throws IOException
	{
		String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(metadata);

		File target = file.getAbsoluteFile();
		File directory = target.getParentFile();
		if(!directory.exists() && !directory.mkdirs())
		{
			throw new IOException("Unable to create directory "+directory.getAbsolutePath());
		}

		File tmp = File.createTempFile(FILE_NAME, ".tmp", directory);
		try
		{
			Files.write(tmp.toPath(), (json+System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
			Files.move(tmp.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		finally
		{
			if(tmp.exists() && !tmp.delete()) {tmp.deleteOnExit();}
		}
	}
}
