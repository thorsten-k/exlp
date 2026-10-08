package org.exlp.maven;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestIgnoreMavenVersionFileMerger
{
	private IgnoreMavenVersionFileMerger merger;

	@BeforeEach
	public void init() {merger = new IgnoreMavenVersionFileMerger();}

	@Test
	public void mergesRuleResourceFromClasspath() throws Exception
	{
		merger.add("exlp/maven/exlp-versions.xml");

		String content = output();

		assertTrue(content.contains("<ruleset"));
		assertTrue(content.contains("comparisonMethod=\"maven\""));
		assertTrue(content.contains("groupId=\"commons-cli\""));
		assertTrue(content.contains("<ignoreVersion>2.0-gt2-pre1</ignoreVersion>"));
	}

	@Test
	public void marksTheResultAsGenerated() throws Exception
	{
		merger.add("exlp/maven/exlp-versions.xml");

		assertTrue(output().contains("Do not modify this file, it is auto generated!"));
	}

	@Test
	public void failsForMissingResource()
	{
		assertThrows(FileNotFoundException.class, () -> merger.add("exlp/maven/does-not-exist.xml"));
	}

	private String output() throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		merger.output(baos);
		return new String(baos.toByteArray(), StandardCharsets.UTF_8);
	}
}
