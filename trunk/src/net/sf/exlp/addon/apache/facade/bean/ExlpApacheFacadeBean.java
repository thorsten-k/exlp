package net.sf.exlp.addon.apache.facade.bean;

import java.io.Serializable;
import java.math.BigInteger;

import javax.ejb.Remote;
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
	
	public double[][] dHitsPerMonth(Double minYear)
	{	
		double[][] result;
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT year(record) AS y,month(record) AS m,count(id)");
			sb.append(" FROM "+ExlpApache.class.getSimpleName());
			sb.append(" WHERE year(record) >= :minYear");
			sb.append(" GROUP BY y,m");
			sb.append(" ORDER BY y,m");
		Query query=getManager().createNativeQuery(sb.toString());
			query.setParameter("minYear", minYear.intValue());
		Object[] rows;
		try{rows = query.getResultList().toArray();}
		catch (NoResultException ex)
		{
			result = new double[0][0];
			return result;
		}
		
		result = new double[3][rows.length];
		for(int i=0;i<rows.length;i++)
		{
			Object[] row = (Object[])rows[i];
			result[0][i] = ((Integer)row[0]).doubleValue();
			result[1][i] = ((Integer)row[1]).doubleValue();
			result[2][i] = ((BigInteger)row[2]).doubleValue();
		}
		
		return result;
	}
	
	public double[][] dHitsPerDay(Double minYear)
	{	
		double[][] result;
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT year(record) AS y,month(record) AS m,day(record) AS d,count(id)");
			sb.append(" FROM "+ExlpApache.class.getSimpleName());
			sb.append(" WHERE year(record) >= :minYear");
			sb.append(" GROUP BY y,m,d");
			sb.append(" ORDER BY y,m,d");
		Query query=getManager().createNativeQuery(sb.toString());
			query.setParameter("minYear", minYear.intValue());
		Object[] rows;
		try{rows = query.getResultList().toArray();}
		catch (NoResultException ex)
		{
			result = new double[0][0];
			return result;
		}
		
		result = new double[4][rows.length];
		for(int i=0;i<rows.length;i++)
		{
			Object[] row = (Object[])rows[i];
			result[0][i] = ((Integer)row[0]).doubleValue();
			result[1][i] = ((Integer)row[1]).doubleValue();
			result[2][i] = ((Integer)row[2]).doubleValue();
			result[3][i] = ((BigInteger)row[3]).doubleValue();
		}
		return result;
	}
}