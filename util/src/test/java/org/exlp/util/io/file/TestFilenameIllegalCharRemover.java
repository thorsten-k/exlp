package org.exlp.util.io.file;

import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.FilenameIllegalCharRemover;

public class TestFilenameIllegalCharRemover extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestFilenameIllegalCharRemover.class);
	
	public TestFilenameIllegalCharRemover()
	{
		
	}
	
	@Test
	public void testFile()
	{
		String test = "xx?";
		String expected = "xx_";
		String actual = FilenameIllegalCharRemover.convert(test);
		
		Assertions.assertEquals(expected,actual);
		
	}
}