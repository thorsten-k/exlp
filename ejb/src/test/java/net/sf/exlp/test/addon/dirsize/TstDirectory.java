package net.sf.exlp.test.addon.dirsize;

import java.util.List;

import net.sf.exlp.addon.common.data.facade.ExlpFacadeFactory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectory;
import net.sf.exlp.addon.dirsize.data.facade.exlp.ExlpDirsizeFacade;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstDirectory
{
	static Log logger = LogFactory.getLog(TstDirectory.class);
		
	private ExlpDirsizeFacade fExlp;
	
	public TstDirectory()
	{
		ExlpFacadeFactory ff = new ExlpFacadeFactory();
		fExlp = ff.getDirsizeFacade();
	}
	
	public void insert()
	{
		ExlpDirectory dir = new ExlpDirectory();
		dir.setPath("/tmp");
		fExlp.newObject(dir);
	}
	
	public void list()
	{
		List<ExlpDirectory> list = fExlp.allExlpDirectories();
		logger.debug(list.size());
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TstDirectory test = new TstDirectory();
		test.list();
		test.insert();
		test.list();
	}
}