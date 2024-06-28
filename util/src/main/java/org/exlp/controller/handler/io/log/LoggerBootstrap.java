package org.exlp.controller.handler.io.log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(LoggerBootstrap.class);
	
	public enum LoadType {Url,File,Resource};
	
	public static boolean log4jInited=false;
	public static boolean debugSystemOut = false;
	
	private String log4j2Name;
	private ArrayList<String> paths;

	
	public static LoggerBootstrap instance() {return new LoggerBootstrap("cli.log4j2.xml");}
	public static LoggerBootstrap instance(String log4j2Name) {return new LoggerBootstrap(log4j2Name);}
	private LoggerBootstrap(String log4j2Name)
	{
		this.log4j2Name=log4j2Name;
	
		paths = new ArrayList<String>();
	}
	
	public LoggerBootstrap path(String path) {paths.add(path); return this;}

	public void init()
	{
		StringBuilder sb = new StringBuilder();
		for(String path : paths)
		{
			sb.setLength(0);
			sb.append(path+"/"+log4j2Name);
			
//			System.out.println("MRL "+MultiResourceLoader.instance().isAvailable(sb.toString())+" "+sb.toString());
			
			Configurator.initialize(null, sb.toString());
//			logger.info("Activated with "+sb.toString());
		}
		logger.info("Log4j2 configured with ["+String.join(", ",paths)+"]");
	}

	public static boolean isLog4jInited() {return log4jInited;}
	public static void debugEnv()
	{
		logger.debug(SystemUtils.JAVA_RUNTIME_NAME+" "+SystemUtils.JAVA_RUNTIME_VERSION);
		logger.debug(SystemUtils.OS_NAME+" ("+SystemUtils.OS_VERSION+") "+SystemUtils.OS_ARCH);
	}
	
	public static void showSystemProperties()
	{
		logger.debug("System Properties:");
		Properties props = System.getProperties();
		Enumeration<Object> enu = props.keys();
		while(enu.hasMoreElements())
		{
			String key=(String)enu.nextElement();
			logger.debug("\t"+key+"="+props.get(key));
		}
	}
}