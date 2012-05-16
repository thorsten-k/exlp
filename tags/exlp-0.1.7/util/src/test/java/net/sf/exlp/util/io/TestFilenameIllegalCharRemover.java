package net.sf.exlp.util.io;

import net.sf.exlp.test.AbstractExlpTst;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFilenameIllegalCharRemover extends AbstractExlpTst
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
		
		Assert.assertEquals(expected,actual);
		
	}
}