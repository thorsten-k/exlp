package net.sf.exlp.addon.exim.data.facade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.exlp.addon.common.data.exception.ExlpNotFoundException;
import net.sf.exlp.addon.common.data.facade.bean.AbstractExlpFacadeBean;
import net.sf.exlp.addon.exim.data.ejb.ExlpEmail;
import net.sf.exlp.addon.exim.data.ejb.ExlpGreylist;
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
	
	public ExlpGreylist fGreylist(Date record, ExlpEmail from, ExlpEmail rcpt) throws ExlpNotFoundException
	{
		ExlpGreylist result=null;
		Query nq = getManager().createNamedQuery("fGreylist");
			nq.setParameter("record", record);
			nq.setParameter("fromId", from.getId());
			nq.setParameter("rcptId", rcpt.getId());
		try	{result=(ExlpGreylist)nq.getSingleResult();}
		catch (NoResultException ex){throw new ExlpNotFoundException("No "+ExlpGreylist.class.getSimpleName()+" found for record="+record+" from="+from.getEmail()+" rcpt="+rcpt.getEmail());}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<ExlpGreylist> fGreylistForRcptInInterval(ExlpEmail rcpt, Date from, Date to)
	{
		List<ExlpGreylist> lResult = new ArrayList<ExlpGreylist>();
		Query q = getManager().createNamedQuery("fGreylistForRcptInInterval");
			q.setParameter("rcptId", rcpt.getId());
			q.setParameter("recordFrom", from);
			q.setParameter("recordTo", to);
		lResult = (List<ExlpGreylist>)q.getResultList();
		return lResult;
	}
}