package org.exlp.util.io.log;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class LoggerInit
{
	static Logger logger = Logger.getLogger(LoggerInit.class);
	
	public enum LoadType {Url,File,Resource};
	
	private static String fSep = System.getProperty("file.separator");
	public static boolean log4jInited=false;
	public static boolean debugSystemOut = false;
	
	private ArrayList<String> alErrors;
	private ArrayList<String> altPaths;
	private String l4jName;
	private List<LoadType> activeLoadTypes;
	
	public static LoggerInit instance() {return new LoggerInit("log4j.xml");}
	public static LoggerInit instance(String l4jName) {return new LoggerInit(l4jName);}
	private LoggerInit(String l4jName)
	{
		this.l4jName=l4jName;
		log4jInited = false;
		altPaths = new ArrayList<String>();
		alErrors = new ArrayList<String>();
		
		activeLoadTypes = new ArrayList<LoadType>();
		for(LoadType lt : LoadType.values()){activeLoadTypes.add(lt);}
	}
	
	public void setAllLoadTypes(LoadType... lt)
	{
		activeLoadTypes.clear();
		for(int i=0;i<lt.length;i++)
		{
			activeLoadTypes.add(lt[i]);
		}
	}
	
	public static void init(String l4jName, String altPath)
	{
		LoggerInit loggerInit = new LoggerInit(l4jName);
		loggerInit.path(altPath);
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
		for(LoadType lt : activeLoadTypes)
		{
			switch (lt)
			{
				case Url:		urlLoad(cl,l4jName);
								for(String path : altPaths)
								{
									if(!log4jInited){urlLoad(cl,path+"/"+l4jName);}
								}
								break;
				case File:		fileLoad(cl,l4jName);
								for(String path : altPaths)
								{
									if(!log4jInited){fileLoad(cl,path+fSep+l4jName);}
								}
								break;
				case Resource:	resourceLoad(cl,l4jName);
								for(String path : altPaths)
								{
									if(!log4jInited){resourceLoad(cl,path+"/"+l4jName);}
								}
								break;
			}
			if(log4jInited){break;}
		}
		if(!log4jInited){for(String s : alErrors){System.err.println(s);}}
	}
	
	public LoggerInit path(String path)
	{
		altPaths.add(path);
		return this;
	}
	
	public void urlLoad(ClassLoader cl, String l4jName)
	{
		URL url = ClassLoader.getSystemResource(l4jName);
		if(url!=null)
		{
			DOMConfigurator.configure(url);
			String msg = "log4j configured with SystemResource: "+url.getFile();
			logger.info(msg);
			if(debugSystemOut) {System.out.println(msg);}
			log4jInited=true;
			
		}
		else{alErrors.add("Not found ClassLoader.getSystemResource("+l4jName+")");}
	}
	
	public void fileLoad(ClassLoader cl,String l4jName)
	{
		File f = new File(".",l4jName);
		
		if(f.exists())
		{
			DOMConfigurator.configure(f.getAbsolutePath());
			String msg = "l4j configured with File: "+f.getAbsolutePath();
			logger.info(msg);
			if(debugSystemOut) {System.out.println(msg);}
			log4jInited=true;
		}
		else {alErrors.add("Not found f.getAbsolutePath("+l4jName+")");}
	}
	
	private void resourceLoad(ClassLoader c, String l4jName)
	{
		URL url = this.getClass().getClassLoader().getResource(l4jName);
		String confInfo = "rsrc "+l4jName;
		if(url!=null)
		{
			DOMConfigurator.configure(url);
			String msg = "log4j configured with "+confInfo;
			logger.info(msg);
			if(debugSystemOut) {System.out.println(msg);}
			log4jInited=true;
		}
		else {alErrors.add("Not found "+confInfo);}
	}
	
	public void showErrors()
	{
		for(String s : alErrors)
		{
			if(!log4jInited)
			{
				System.err.println(s);
			}
			else
			{
				logger.debug(s);
			}
		}
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