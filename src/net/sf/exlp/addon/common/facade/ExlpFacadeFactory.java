package net.sf.exlp.addon.common.facade;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.exlp.addon.apache.facade.bean.ExlpApacheFacadeBean;
import net.sf.exlp.addon.apache.facade.exlp.ExlpApacheFacade;
import net.sf.exlp.addon.common.facade.bean.ExlpCommonFacadeBean;
import net.sf.exlp.addon.common.facade.exlp.ExlpCommonFacade;
import net.sf.exlp.util.data.facade.ExlpContextFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExlpFacadeFactory
{
	static Log logger = LogFactory.getLog(ExlpFacadeFactory.class);
	
	private InitialContext context;
	private String jbossServer;
	private String contextPrefix;
	
	public ExlpFacadeFactory(String contextPrefix){this("localhost:1099",contextPrefix);}
	
	public ExlpFacadeFactory(String jbossServer,String contextPrefix)
	{
		this.contextPrefix=contextPrefix;
		this.jbossServer=jbossServer;
	}
	
	private void initContext()
	{
		
		try{context = ExlpContextFactory.getJbossContext(jbossServer);}
		catch (NamingException e){exit(e);}
	}
	
	public ExlpCommonFacade getCommonFacade()
	{
		if(context==null){initContext();}
		ExlpCommonFacade f=null;
		try{f = (ExlpCommonFacade)context.lookup(contextPrefix+"/"+ExlpCommonFacadeBean.class.getSimpleName()+"/remote");}
		catch (NamingException e){exit(e);}
		return f;
	}
	
	public ExlpApacheFacade getApacheFacade()
	{
		if(context==null){initContext();}
		ExlpApacheFacade f=null;
		try{f = (ExlpApacheFacade)context.lookup(contextPrefix+"/"+ExlpApacheFacadeBean.class.getSimpleName()+"/remote");}
		catch (NamingException e){exit(e);}
		return f;
	}
	
	private void exit(Exception e)
	{
		logger.error(e);
		logger.fatal("Error binding remote facade.");
		logger.fatal("System will exit");
		System.exit(-1);
	}
}