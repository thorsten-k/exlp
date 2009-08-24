package net.sf.exlp.io;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class LoggerInit
{
	static Logger logger = Logger.getLogger(LoggerInit.class);
	
	private static String fSep = System.getProperty("file.separator");
	public static boolean log4jInited=false;
	private ArrayList<String> alErrors;
	private enum LoadType {Url,File,Resource};
	private ArrayList<String> altPaths;
	private String l4jName;
	
	public LoggerInit(String l4jName)
	{
		this.l4jName=l4jName;
		log4jInited = false;
		altPaths = new ArrayList<String>();
		alErrors = new ArrayList<String>();
	}
	
	public static void init(String l4jName, String altPath)
	{
		LoggerInit loggerInit = new LoggerInit(l4jName);
		loggerInit.addAltPath(altPath);
		loggerInit.init();
	}
	
	public static void init(String l4jName)
	{
		LoggerInit loggerInit = new LoggerInit(l4jName);
		loggerInit.init();
	}
	
	public void init()
	{
		ClassLoader cl = this.getClass().getClassLoader();
		for(LoadType lt : LoadType.values())
		{
			switch (lt)
			{
				case Url:		urlLoad(cl,l4jName);
								for(String path : altPaths)
								{
									if(!log4jInited){urlLoad(cl,path+fSep+l4jName);}
								}
								break;
				case File:		directLoad(cl,l4jName);
								for(String path : altPaths)
								{
									if(!log4jInited){directLoad(cl,path+fSep+l4jName);}
								}
								break;
				case Resource:	resourceLoad(cl,l4jName);
								for(String path : altPaths)
								{
									if(!log4jInited){resourceLoad(cl,path+fSep+l4jName);}
								}
								break;
			}
			if(log4jInited){break;}
		}
		if(!log4jInited){for(String s : alErrors){System.err.println(s);}}
	}
	
	public void addAltPath(String path)
	{
		altPaths.add(path);
	}
	
	public void urlLoad(ClassLoader cl,String l4jName)
	{
		URL url = ClassLoader.getSystemResource(l4jName);
		if(url!=null)
		{
			DOMConfigurator.configure(url);
			logger.info("log4j configured with SystemResource: "+url.getFile());
			log4jInited=true;
		}
		else {alErrors.add("Not found ClassLoader.getSystemResource("+l4jName+")");}
	}
	
	public void directLoad(ClassLoader cl,String l4jName)
	{
		File f = new File(l4jName);
		
		if(f.exists())
		{
			DOMConfigurator.configure(f.getAbsolutePath());
			logger.info("log4j configured with File: "+f.getAbsolutePath());
			log4jInited=true;
		}
		else {alErrors.add("Not found f.getAbsolutePath("+l4jName+")");}
	}
	
	private void resourceLoad(ClassLoader cl,String l4jName)
	{
		URL url = this.getClass().getClassLoader().getResource(l4jName);
		String confInfo = "LoggerInit.getClass().getClassLoader().getResource("+l4jName+")";
		if(url!=null)
		{	//Konfiguration f�rs Package
			DOMConfigurator.configure(url);
			logger.info("log4j configured with "+confInfo);
			log4jInited=true;
		}
		else {alErrors.add("Not found "+confInfo);}
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
