package net.sf.exlp.util.net.ejb;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public abstract class AbstractExlpFacadeFactory implements ExlpFacadeFactory
{
	final static Marker fatal = MarkerFactory.getMarker("FATAL");
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpFacadeFactory.class);
	
	private InitialContext context;
	protected String jbossServer;
	private String contextPrefix;

	protected Map<String,Object> mFacadeCache;
	
	public AbstractExlpFacadeFactory(String jbossServer,String contextPrefix)
	{
		this.jbossServer=jbossServer;
		this.contextPrefix=contextPrefix;
		mFacadeCache = new Hashtable<String,Object>();
	}
	
	public AbstractExlpFacadeFactory(String contextPrefix){this("localhost:1099",contextPrefix);}
	
	public AbstractExlpFacadeFactory(Configuration config)
	{
		this(config.getString("net/jboss/@host")+":"+config.getInt("net/jboss/@port"),config.getString("net/jboss/@context"));
	}

	public String getContextPrefix() {return contextPrefix+"/";}
	
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
		logger.error("Error binding remote facade.",e);
		logger.error(fatal,"System will exit",e);
		System.exit(-1);
	}
}