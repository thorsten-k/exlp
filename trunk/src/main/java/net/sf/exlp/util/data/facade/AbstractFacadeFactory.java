package net.sf.exlp.util.data.facade;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractFacadeFactory implements FacadeFactory
{
	static Log logger = LogFactory.getLog(AbstractFacadeFactory.class);
	
	private InitialContext context;
	protected String jbossServer;
	protected String contextPrefix;

	protected Map<String,Object> mFacadeCache;
	
	public AbstractFacadeFactory(String jbossServer,String contextPrefix)
	{
		this.jbossServer=jbossServer;
		this.contextPrefix=contextPrefix;
		mFacadeCache = new Hashtable<String,Object>();
	}
	
	public AbstractFacadeFactory(String contextPrefix){this("localhost:1099",contextPrefix);}
	
	public AbstractFacadeFactory(Configuration config)
	{
		this(config.getString("net/jboss/@host")+":"+config.getInt("net/jboss/@port"),config.getString("net/jboss/@context"));
	}

	public String getContextPrefix() {return contextPrefix;}
	
	public InitialContext getContext()
	{
		if(context==null)
		{
			try{context = ExlpContextFactory.getJbossContext(jbossServer);}
			catch (NamingException e){exit(e);}
		}
		return context;
	}
	
	protected void exit(Exception e)
	{
		logger.error(e);
		logger.fatal("Error binding remote facade.");
		logger.fatal("System will exit");
		System.exit(-1);
	}
}