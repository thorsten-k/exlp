package net.sf.exlp.addon.common.facade.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.addon.common.facade.exlp.ExlpCommonFacade;

import org.apache.log4j.Logger;

@Stateful
@Remote
public class ExlpCommonFacadeBean extends AbstractExlpFacadeBean implements ExlpCommonFacade, Serializable
{
	private static Logger logger = Logger.getLogger(ExlpCommonFacadeBean.class);
	static final long serialVersionUID=10;
	
	public ExlpHost fExlpHost(String ip)
	{
		ExlpHost result=null;
		Query nq = getManager().createNamedQuery("fExlpHost");
			nq.setParameter("ip", ip);
		try	{result=(ExlpHost)nq.getSingleResult();}
		catch (NoResultException ex){}
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
		
		logger.info(sql);
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
	
	@Remove
	public void checkout(){logger.debug("Checkout");}	
}