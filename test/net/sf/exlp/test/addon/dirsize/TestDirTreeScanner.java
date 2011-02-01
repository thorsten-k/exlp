package net.sf.exlp.test.addon.dirsize;

import java.io.FileNotFoundException;

import net.sf.exlp.addon.dirsize.data.ejb.ExlpFile;
import net.sf.exlp.addon.dirsize.data.jaxb.Dir;
import net.sf.exlp.addon.dirsize.data.jaxb.DirFile;
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
		Dir dir = scanner.getDir("/Users/thorsten/Documents/workspace/3.6.0/ExLP/dist");
		JaxbUtil.debug(dir);
		DirFile df = scanner.getDirFile("/Users/thorsten/Documents/workspace/3.6.0/ExLP/dist");
		JaxbUtil.debug(df);
		
		ExlpFile ef = scanner.getExlpFile("/Users/thorsten/Documents/workspace/3.6.0/ExLP/dist");
		logger.debug("Debug "+ef.getChilds().size());
		for (int i=0;i<ef.getChilds().size();i++)
		{
			logger.debug(i);
			ExlpFile cf = ef.getChilds().get(i);
			logger.debug("\t"+cf.getName());
		}
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