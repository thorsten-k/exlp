package net.sf.exlp.addon.common.data.facade;

import javax.naming.NamingException;

import net.sf.exlp.addon.apache.facade.bean.ExlpApacheFacadeBean;
import net.sf.exlp.addon.apache.facade.exlp.ExlpApacheFacade;
import net.sf.exlp.addon.common.data.facade.bean.ExlpCommonFacadeBean;
import net.sf.exlp.addon.common.data.facade.exlp.ExlpCommonFacade;
import net.sf.exlp.addon.dirsize.data.facade.bean.ExlpDirsizeFacadeBean;
import net.sf.exlp.addon.dirsize.data.facade.exlp.ExlpDirsizeFacade;
import net.sf.exlp.addon.exim.data.facade.bean.ExlpEximFacadeBean;
import net.sf.exlp.addon.exim.data.facade.exlp.ExlpEximFacade;
import net.sf.exlp.util.net.ejb.AbstractExlpFacadeFactory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExlpFacadeFactory extends AbstractExlpFacadeFactory
{
	static Log logger = LogFactory.getLog(ExlpFacadeFactory.class);
	
	public ExlpFacadeFactory(){super("exlp");}
	public ExlpFacadeFactory(String contextPrefix){super(contextPrefix);}
	public ExlpFacadeFactory(String jbossServer,String contextPrefix) {super(jbossServer,contextPrefix);}
	public ExlpFacadeFactory(Configuration config){super(config);}
	
	public ExlpCommonFacade getCommonFacade()
	{
		ExlpCommonFacade f=null;
		try{f = (ExlpCommonFacade)getContext().lookup(contextPrefix+"/"+ExlpCommonFacadeBean.class.getSimpleName()+"/remote");}
		catch (NamingException e){exit(e);}
		return f;
	}
	
	public ExlpApacheFacade getApacheFacade()
	{
		ExlpApacheFacade f=null;
		try{f = (ExlpApacheFacade)getContext().lookup(contextPrefix+"/"+ExlpApacheFacadeBean.class.getSimpleName()+"/remote");}
		catch (NamingException e){exit(e);}
		return f;
	}
	
	public ExlpEximFacade getEximFacade()
	{
		ExlpEximFacade f=null;
		try{f = (ExlpEximFacade)getContext().lookup(contextPrefix+"/"+ExlpEximFacadeBean.class.getSimpleName()+"/remote");}
		catch (NamingException e){exit(e);}
		return f;
	}
	
	public ExlpDirsizeFacade getDirsizeFacade()
	{
		ExlpDirsizeFacade f=null;
		try{f = (ExlpDirsizeFacade)getContext().lookup(contextPrefix+"/"+ExlpDirsizeFacadeBean.class.getSimpleName()+"/remote");}
		catch (NamingException e){exit(e);}
		return f;
	}
}