package net.sf.exlp.test.addon.dirsize;

import java.io.FileNotFoundException;

import net.sf.exlp.addon.dirsize.data.ejb.ExlpFile;
import net.sf.exlp.addon.dirsize.data.jaxb.Dir;
import net.sf.exlp.addon.dirsize.data.jaxb.DirFile;
import net.sf.exlp.addon.dirsize.util.DirTreeScanner;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstDirTreeScanner
{
	static Log logger = LogFactory.getLog(TstDirTreeScanner.class);
	
	public TstDirTreeScanner()
	{

	}
	
	public void scan(String dirName) throws FileNotFoundException
	{
		DirTreeScanner scanner = new DirTreeScanner();
		Dir dir = scanner.getDir(dirName);
		JaxbUtil.debug(dir);
		
		DirFile df = scanner.getDirFile(dirName);
		JaxbUtil.debug(df);
		
		ExlpFile ef = scanner.getExlpFile(dirName);
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
		
		TstDirTreeScanner test = new TstDirTreeScanner();
		test.scan("/tmp/ftp/project");
	}
}