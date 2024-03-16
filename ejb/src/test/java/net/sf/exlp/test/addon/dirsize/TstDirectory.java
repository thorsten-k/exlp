package net.sf.exlp.test.addon.dirsize;

import java.util.List;

import org.exlp.test.ExlpEjbBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.addon.common.data.facade.ExlpFacadeFactory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectory;
import net.sf.exlp.addon.dirsize.data.facade.exlp.ExlpDirsizeFacade;

public class TstDirectory
{
	final static Logger logger = LoggerFactory.getLogger(TstDirectory.class);
		
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
		logger.debug("Size: "+list.size());
	}
	
	public static void main (String[] args) throws Exception
	{
		ExlpEjbBootstrap.init();
		
		TstDirectory test = new TstDirectory();
		test.list();
		test.insert();
		test.list();
	}
}