package net.sf.exlp.util.io;

import java.io.File;

import net.sf.exlp.test.AbstractExlpTst;
import net.sf.exlp.test.ExlpTstBootstrap;

import org.apache.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRelativePathFactory extends AbstractExlpTst
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
	
		Assert.assertEquals("target/x1.txt",sRpf);
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
		
		Assert.assertEquals(expected, actual);
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
		
		Assert.assertEquals(expected, actual);
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
	
		Assert.assertEquals("X",actual);
	}
	
	@Test
	public void test()
	{
		File parent = new File("src/test/java");
		File child = new File("src/test/java/net/sf/exlp/test/util/io");
	
		RelativePathFactory rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.UNIX);
		String sRelative = rpf.relativate(parent, child);
		
		logger.debug(sRelative);
		
		if(SystemUtils.IS_OS_WINDOWS){Assert.assertEquals("net/sf/exlp/test/util/io",sRelative);}
	}
	
	@Test
	public void equalFiles()
	{
		File parent = new File("src/test/java");
		File child = new File("src/test/java");
		
		RelativePathFactory rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.UNIX);
		String sRelative = rpf.relativate(parent, child);
		
		logger.debug(sRelative);
		Assert.assertEquals(".", sRelative);
	}
	
	@Test
	public void fixedConstructor()
	{
		File parent = new File("src/test/java");
		File child = new File("src/test/java/test");
		
		RelativePathFactory rpf = new RelativePathFactory(parent,RelativePathFactory.PathSeparator.UNIX);
		String sRelative = rpf.relativate(parent, child);
		
		logger.debug(sRelative);
		Assert.assertEquals("test", sRelative);
	}
	
	public static void main(String[] args)
    {
		ExlpTstBootstrap.init();
		
		TestRelativePathFactory test = new TestRelativePathFactory();
		test.fixedConstructor();
    }
}