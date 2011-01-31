package net.sf.exlp.test.addon.dirsize;

import java.io.FileNotFoundException;

import net.sf.exlp.addon.dirsize.data.jaxb.Dir;
import net.sf.exlp.addon.dirsize.util.DirTreeScanner;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDirTreeScanner
{
	static Log logger = LogFactory.getLog(TestDirTreeScanner.class);
	
	public TestDirTreeScanner()
	{
		
	}
	
	public void scan() throws FileNotFoundException
	{
		DirTreeScanner scanner = new DirTreeScanner();
		Dir dir = scanner.getDirTree("/Users/thorsten/Documents/workspace/3.6.0/ExLP/dist");
		JaxbUtil.debug(dir);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestDirTreeScanner test = new TestDirTreeScanner();
		test.scan();
	}
}