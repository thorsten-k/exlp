package net.sf.exlp.test.util.io;

import java.io.File;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.RelativePathFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRelativePathFactory
{
	static Log logger = LogFactory.getLog(TestRelativePathFactory.class);
	
	public TestRelativePathFactory()
	{
		
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
	
	@Test
	public void testFile()
	{
		File fFixed = new File(".");
		File fRelative = new File(".","target/x1.txt");
		
		RelativePathFactory rpf = new RelativePathFactory();
		String sRpf = rpf.relativate(fFixed.getAbsolutePath(), fRelative.getAbsolutePath());
		logger.debug("Current: "+fFixed.getAbsolutePath());
		logger.debug("Absolute: "+fRelative.getAbsolutePath());
		logger.debug("Relative: "+sRpf);
	
		Assert.assertEquals(FilenameUtils.separatorsToUnix(sRpf),"target/x1.txt");
	}
	
	@Test
	public void testWinWithUnixSeparator()
	{
		String sBase = "C:\\Users\\Base";
		String sAbsolute = "C:\\Users\\Base\\x.3\\a.txt";
		
		RelativePathFactory rpf = new RelativePathFactory(true,true);
		String actual = rpf.relativate(sBase, sAbsolute);
		String expected = "x.3/a.txt";
		
		logger.debug("Base: "+sBase);
		logger.debug("Absolute: "+sAbsolute);
		logger.debug("expected: "+expected);
		logger.debug("actual: "+actual);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testWinWithWindowsSeparator()
	{
		String sBase = "C:\\Users\\Base";
		String sAbsolute = "C:\\Users\\Base\\x.3\\a.txt";
		
		RelativePathFactory rpf = new RelativePathFactory(true,false);
		String actual = rpf.relativate(sBase, sAbsolute);
		String expected = "x.3\\a.txt";
		
		logger.debug("Base: "+sBase);
		logger.debug("Absolute: "+sAbsolute);
		logger.debug("expected: "+expected);
		logger.debug("actual: "+actual);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testString()
	{
		String sFixed = "/Base";
		String sRelative = "/Base/X";
		
		RelativePathFactory rpf = new RelativePathFactory();
		String sRpf = rpf.relativate(sFixed, sRelative);
		logger.debug("Current: "+sFixed);
		logger.debug("Absolute: "+sRelative);
		logger.debug("Relative: "+sRpf);
	
		Assert.assertEquals(FilenameUtils.separatorsToUnix(sRpf),"X");
	}
	
	@Test
	public void test()
	{
		File parent = new File("src/test/java");
		File child = new File("src/test/java/net/sf/exlp/test/util/io");
	
		RelativePathFactory rpf = new RelativePathFactory();
		String sRelative = rpf.relativate(parent, child);
		
		logger.debug(sRelative);
		
		if(SystemUtils.IS_OS_WINDOWS){Assert.assertEquals("net/sf/exlp/test/util/io",sRelative);}
	}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		TestRelativePathFactory test = new TestRelativePathFactory();
		test.testWinWithWindowsSeparator();
    }
}