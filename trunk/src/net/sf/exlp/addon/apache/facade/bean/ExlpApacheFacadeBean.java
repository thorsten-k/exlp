package net.sf.exlp.addon.apache.facade.bean;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.exlp.addon.apache.ejb.ExlpApache;
import net.sf.exlp.addon.apache.facade.exlp.ExlpApacheFacade;
import net.sf.exlp.addon.common.facade.bean.AbstractExlpFacadeBean;

import org.apache.log4j.Logger;

@Stateful
@Remote
public class ExlpApacheFacadeBean extends AbstractExlpFacadeBean implements ExlpApacheFacade, Serializable
{
	private static Logger logger = Logger.getLogger(ExlpApacheFacadeBean.class);
	static final long serialVersionUID=10;
	
	public ExlpApache nExlpApache(ExlpApache apache)
	{
		Query nq = getManager().createNamedQuery("fExlpApache");
			nq.setParameter("record", apache.getRecord());
			nq.setParameter("url", apache.getUrl());
			nq.setParameter("hostid", apache.getHost().getId());
		try	{apache=(ExlpApache)nq.getSingleResult();}
		catch (NoResultException ex){getManager().persist(apache);}
		return apache;
	}
	
	@Remove
	public void checkout(){logger.debug("Checkout");}	
}