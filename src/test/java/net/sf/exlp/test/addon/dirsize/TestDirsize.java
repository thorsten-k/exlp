package net.sf.exlp.test.addon.dirsize;

import java.util.Date;
import java.util.List;

import net.sf.exlp.addon.common.data.facade.ExlpFacadeFactory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectorySize;
import net.sf.exlp.addon.dirsize.data.facade.exlp.ExlpDirsizeFacade;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDirsize
{
	static Log logger = LogFactory.getLog(TestDirsize.class);
	
	private ExlpDirsizeFacade fExlp;
	
	public TestDirsize()
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
		logger.debug(dir);
		
		ExlpDirectorySize size = new ExlpDirectorySize();
		size.setSize(100);
		size.setDirectory(dir);
		size.setRecord(new Date());
		
		fExlp.addSize(dir, size);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestDirsize test = new TestDirsize();
		test.insert();
	}
}