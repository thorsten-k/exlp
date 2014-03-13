package net.sf.exlp.addon.dirsize.data.facade.exlp;

import java.util.List;

import net.sf.exlp.addon.common.data.facade.exlp.ExlpFacade;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectorySize;

public interface ExlpDirsizeFacade extends ExlpFacade
{
	List<ExlpDirectory> allExlpDirectories();
	
	//Size
	ExlpDirectory addSize(ExlpDirectory dir, ExlpDirectorySize size);
}
