package net.sf.exlp.addon.dirsize.data.facade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.Query;

import net.sf.exlp.addon.common.data.facade.bean.AbstractExlpFacadeBean;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectorySize;
import net.sf.exlp.addon.dirsize.data.facade.exlp.ExlpDirsizeFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateful
@Remote
public class ExlpDirsizeFacadeBean extends AbstractExlpFacadeBean implements ExlpDirsizeFacade, Serializable
{
	final static Logger logger = LoggerFactory.getLogger(ExlpDirsizeFacadeBean.class);
	static final long serialVersionUID=1;
	
	@SuppressWarnings("unchecked")
	public List<ExlpDirectory> allExlpDirectories()
	{
		List<ExlpDirectory> lResult = new ArrayList<ExlpDirectory>();
		Query q = getManager().createNamedQuery("allExlpDirectories");
		lResult = (List<ExlpDirectory>)q.getResultList();
		return lResult;
	}
	
	public ExlpDirectory addSize(ExlpDirectory dir, ExlpDirectorySize size)
	{
		dir = this.getManager().find(ExlpDirectory.class, dir.getId());
		
		size.setDirectory(dir);
		this.getManager().persist(size);
		
		dir.setRecentSize(size);
		this.getManager().merge(dir);
		
		return dir;
	}
}