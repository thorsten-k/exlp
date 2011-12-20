package net.sf.exlp.addon.common.data.facade.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.exlp.addon.common.data.ejb.ExlpCountry;
import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.addon.common.data.exception.ExlpNotFoundException;
import net.sf.exlp.addon.common.data.facade.exlp.ExlpCommonFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateful
@Remote
public class ExlpCommonFacadeBean extends AbstractExlpFacadeBean implements ExlpCommonFacade, Serializable
{
	final static Logger logger = LoggerFactory.getLogger(ExlpCommonFacadeBean.class);
	static final long serialVersionUID=10;
	
	public ExlpHost fExlpHost(String ip) throws ExlpNotFoundException
	{
		ExlpHost result=null;
		Query nq = getManager().createNamedQuery("fExlpHost");
			nq.setParameter("ip", ip);
		try	{result=(ExlpHost)nq.getSingleResult();}
		catch (NoResultException ex){throw new ExlpNotFoundException("No "+ExlpHost.class.getSimpleName()+" found for ip="+ip);}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<ExlpHost> fExlpHostWihtoutCountry(int limit)
	{
		List<ExlpHost> result = new ArrayList<ExlpHost>();
		
		StringBuffer sql = new StringBuffer();
			sql.append("SELECT id");
			sql.append(" FROM "+ExlpHost.class.getSimpleName());
			sql.append(" WHERE country IS NULL");
			sql.append(" LIMIT "+limit);
		
		logger.debug("SQL: "+sql);
		Query q = getManager().createNativeQuery(sql.toString());
		List<Object> l = q.getResultList();

		for (Iterator<Object> it = l.iterator(); it.hasNext(); )
		{ 
			BigInteger id = (BigInteger)it.next();
			ExlpHost host = (ExlpHost)findObject(ExlpHost.class, id.longValue());
			result.add(host);
		}
		return result;
	}
	
	public ExlpCountry fCountryByCode(String code) throws ExlpNotFoundException
	{
		ExlpCountry result=null;
		Query nq = getManager().createNamedQuery("fExlpCountryByCode");
			nq.setParameter("code", code);
		try	{result=(ExlpCountry)nq.getSingleResult();}
		catch (NoResultException ex){throw new ExlpNotFoundException("No "+ExlpCountry.class.getSimpleName()+" found for code="+code);}
		return result;	
	}
}