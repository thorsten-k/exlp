package net.sf.exlp.addon.common.data.facade.bean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remove;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.exlp.addon.common.data.exception.ExlpIntegrityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@PersistenceContext(name="exlpEM", unitName="exlp")
public abstract class AbstractExlpFacadeBean
{
	static Log logger = LogFactory.getLog(AbstractExlpFacadeBean.class);
	static final long serialVersionUID=10;
	
	private EntityManager manager;
	
	protected EntityManager getManager()
	{
		if(manager==null)
		{
			try
			{
				InitialContext ctx = new InitialContext();
				manager= (EntityManager)ctx.lookup("java:comp/env/exlpEM");
			}
			catch (NamingException e) {logger.error(e);}
			logger.debug("manager==null .... but looked it up");
		}
		else{logger.debug("Manager active ...");}
		return manager;
	}
	
	public void deleteObject(Object o) throws ExlpIntegrityException
	{
		try
		{
			o=getManager().merge(o);
			getManager().remove(o);
			getManager().flush();
		}
		catch(javax.persistence.PersistenceException e)
		{
			if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
			{
				throw new ExlpIntegrityException("Delete Referential Integrity check failed for "+o.getClass().getSimpleName()+". Object may be used somewhere else.");
			}
			throw(e);
		}
	}
	
	public Object findObject(Class<?> c,int id)
	{
		return getManager().find(c,id);
	}
	public Object findObject(Class<?> c,long id){return getManager().find(c,id);}
	public List<?> findObjects(String tableName) {return getManager().createQuery("FROM "+tableName).getResultList();}
	
	public Object newObject(Object o)
	{
		try
		{
			getManager().persist(o);
		}
		catch (EJBException ex)
		{
			if(ex.getCausedByException().getClass().getSimpleName().equals(EntityExistsException.class.getSimpleName()))
			{
				logger.warn(ex.getCausedByException());
			}
			else {throw ex;}
		}
	    return o;
	}
	
	public Object updateObject(Object o) {getManager().merge(o);return o;}
	
	@Remove
	public void checkout(){logger.debug("Checkout");}	
}