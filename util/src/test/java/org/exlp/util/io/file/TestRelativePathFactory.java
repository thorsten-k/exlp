package org.exlp.util.io.file;

import java.io.File;

import org.apache.commons.lang3.SystemUtils;
import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.RelativePathFactory;

public class TestRelativePathFactory extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestRelativePathFactory.class);
	
	public TestRelativePathFactory()
	{
		
	}
	
	@Test
	public void testFile()
	{
		File fFixed = new File(".");
		File fRelative = new File(".","target/x1.txt");
		
		RelativePathFactory rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.UNIX);
		String sRpf = rpf.relativate(fFixed.getAbsolutePath(), fRelative.getAbsolutePath());
		logger.debug("Current: "+fFixed.getAbsolutePath());
		logger.debug("Absolute: "+fRelative.getAbsolutePath());
		logger.debug("Relative: "+sRpf);
	
		Assertions.assertEquals("target/x1.txt",sRpf);
	}
	
	@Test
	public void testWinWithUnixSeparator()
	{
		String sBase = "C:\\Users\\Base";
		String sAbsolute = "C:\\Users\\Base\\x.3\\a.txt";
		
		RelativePathFactory rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.UNIX);
		String actual = rpf.relativate(sBase, sAbsolute);
		String expected = "x.3/a.txt";
		
		logger.debug("Base: "+sBase);
		logger.debug("Absolute: "+sAbsolute);
		logger.debug("expected: "+expected);
		logger.debug("actual: "+actual);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testWinWithWindowsSeparator()
	{
		String sBase = "C:\\Users\\Base";
		String sAbsolute = "C:\\Users\\Base\\x.3\\a.txt";
		
		RelativePathFactory rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.WINDOWS);
		String actual = rpf.relativate(sBase, sAbsolute);
		String expected = "x.3\\a.txt";
		
		logger.debug("Base: "+sBase);
		logger.debug("Absolute: "+sAbsolute);
		logger.debug("expected: "+expected);
		logger.debug("actual: "+actual);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testString()
	{
		String sFixed = "/Base";
		String sRelative = "/Base/X";
		
		RelativePathFactory rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.UNIX);
		String actual = rpf.relativate(sFixed, sRelative);
		logger.debug("Current: "+sFixed);
		logger.debug("Absolute: "+sRelative);
		logger.debug("Relative: "+actual);
	
		Assertions.assertEquals("X",actual);
	}
	
	@Test
	public void test()
	{
		File parent = new File("src/test/java");
		File child = new File("src/test/java/net/sf/exlp/test/util/io");
	
		RelativePathFactory rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.UNIX);
		String sRelative = rpf.relativate(parent, child);
		
		logger.debug(sRelative);
		
		if(SystemUtils.IS_OS_WINDOWS){Assertions.assertEquals("net/sf/exlp/test/util/io",sRelative);}
	}
	
	@Test
	public void equalFiles()
	{
		File parent = new File("src/test/java");
		File child = new File("src/test/java");
		
		RelativePathFactory rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.UNIX);
		String sRelative = rpf.relativate(parent, child);
		
		logger.debug(sRelative);
		Assertions.assertEquals(".", sRelative);
	}
	
	@Test
	public void fixedConstructor()
	{
		File parent = new File("src/test/java");
		File child = new File("src/test/java/test");
		
		RelativePathFactory rpf = new RelativePathFactory(parent,RelativePathFactory.PathSeparator.UNIX);
		String sRelative = rpf.relativate(parent, child);
		
		logger.debug(sRelative);
		Assertions.assertEquals("test", sRelative);
	}
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
		
		TestRelativePathFactory test = new TestRelativePathFactory();
		test.fixedConstructor();
    }
}