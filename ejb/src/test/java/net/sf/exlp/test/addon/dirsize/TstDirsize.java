package net.sf.exlp.test.addon.dirsize;

import java.util.Date;
import java.util.List;

import net.sf.exlp.addon.common.data.facade.ExlpFacadeFactory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectorySize;
import net.sf.exlp.addon.dirsize.data.facade.exlp.ExlpDirsizeFacade;
import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstDirsize
{
	final static Logger logger = LoggerFactory.getLogger(TstDirsize.class);
	
	private ExlpDirsizeFacade fExlp;
	
	public TstDirsize()
	{
		ExlpFacadeFactory ff = new ExlpFacadeFactory();
		fExlp = ff.getDirsizeFacade();
	}
	
	public ExlpDirectory getFirst()
	{
		List<ExlpDirectory> list = fExlp.allExlpDirectories();
		return list.get(0);
	}
	
	public void insert()
	{
		ExlpDirectory dir = getFirst();
		logger.debug(dir.toString());
		
		ExlpDirectorySize size = new ExlpDirectorySize();
		size.setSize(100);
		size.setDirectory(dir);
		size.setRecord(new Date());
		
		fExlp.addSize(dir, size);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("resources/config");
			loggerInit.init();
		
		TstDirsize test = new TstDirsize();
		test.insert();
	}
}