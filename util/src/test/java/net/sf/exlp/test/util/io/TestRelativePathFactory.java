package net.sf.exlp.test.util.io;

import java.io.File;

import junit.framework.TestCase;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.RelativePathFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Before;

public class TestRelativePathFactory extends TestCase
{
	static Log logger = LogFactory.getLog(TestRelativePathFactory.class);
	
	public TestRelativePathFactory()
	{
		
	}
	
	@Before
	public void initx()
	{
		System.out.println("Test");
		Logger root = Logger.getRootLogger();
		root.addAppender(new ConsoleAppender(
		    new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
	}
	
	public void testFile()
	{
		File fFixed = new File(".");
		File fRelative = new File(".","target/x1.txt");
		
		RelativePathFactory rpf = new RelativePathFactory();
		String sRpf = rpf.relativate(fFixed.getAbsolutePath(), fRelative.getAbsolutePath());
		logger.debug("Current: "+fFixed.getAbsolutePath());
		logger.debug("Absolute: "+fRelative.getAbsolutePath());
		logger.debug("Relative: "+sRpf);
	
		assertEquals(FilenameUtils.separatorsToUnix(sRpf),"target/x1.txt");
	}
	
	public void testWin()
	{
		String sBase = "C:\\Users\\Base";
		String sAbsolute = "C:\\Users\\Base\\x.3\\a.txt";
		
		RelativePathFactory rpf = new RelativePathFactory();
		String sRpf = rpf.relativate(sBase, sAbsolute);
		logger.debug("Base: "+sBase);
		logger.debug("Absolute: "+sAbsolute);
		logger.debug("Relative: "+sRpf);
		
		assertEquals(FilenameUtils.separatorsToUnix(sRpf),"x.3/a.txt");
	}
	
	public void testString()
	{
		String sFixed = "/Base";
		String sRelative = "/Base/X";
		
		RelativePathFactory rpf = new RelativePathFactory();
		String sRpf = rpf.relativate(sFixed, sRelative);
		logger.debug("Current: "+sFixed);
		logger.debug("Absolute: "+sRelative);
		logger.debug("Relative: "+sRpf);
	
		assertEquals(FilenameUtils.separatorsToUnix(sRpf),"X");
	}
	
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		TestRelativePathFactory test = new TestRelativePathFactory();
		test.testFile();
		test.testString();
		test.testWin();
    }
}