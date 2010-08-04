package net.sf.exlp.addon.exim.data.facade.bean;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.exlp.addon.common.data.facade.bean.AbstractExlpFacadeBean;
import net.sf.exlp.addon.exim.data.ejb.ExlpEmail;
import net.sf.exlp.addon.exim.data.facade.exlp.ExlpEximFacade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateful
@Remote
public class ExlpEximFacadeBean extends AbstractExlpFacadeBean implements ExlpEximFacade, Serializable
{
	static Log logger = LogFactory.getLog(ExlpEximFacadeBean.class);
	static final long serialVersionUID=10;
	
	public ExlpEmail fEmail(String email)
	{
		ExlpEmail result = null;
		Query nq = getManager().createNamedQuery("fEmail");
			nq.setParameter("email", email);
		try	{result=(ExlpEmail)nq.getSingleResult();}
		catch (NoResultException ex)
		{
			result = new ExlpEmail();
			result.setEmail(email);
			getManager().persist(result);
		}
		return result;
	}
}