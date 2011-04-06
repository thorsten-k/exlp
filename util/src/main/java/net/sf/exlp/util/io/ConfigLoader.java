package net.sf.exlp.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigLoader
{
	static Log logger = LogFactory.getLog(ConfigLoader.class);
	private static enum Typ{UNKNOWN,XML,PROPERTIES}
	
	private static CompositeConfiguration c;
	
	private static XMLConfiguration xmlSave;
	private static ArrayList<String> alConfigNames;
	
	
	public synchronized static void clear()
	{
		alConfigNames = new ArrayList<String>();
	}
	
	public synchronized static void add(String configName)
	{
		if(alConfigNames==null){alConfigNames = new ArrayList<String>();}
		alConfigNames.add(configName);
	}
	
	public synchronized static Configuration init()
	{
		try
		{
			XPathExpressionEngine exEngine = new XPathExpressionEngine();
			c = new CompositeConfiguration();
			for(String configName : alConfigNames)
			{
				switch(getTyp(configName))
				{
					case XML:	XMLConfiguration xCnf = new XMLConfiguration(configName);
								xCnf.setExpressionEngine(exEngine);
								if(xmlSave==null){xmlSave=xCnf;}
								c.addConfiguration(xCnf);break;
					case PROPERTIES:	PropertiesConfiguration pCnf = new PropertiesConfiguration(configName);
										c.addConfiguration(pCnf);break;
					default: logger.warn("Unknwon Resource: "+configName);break;
				}
			}
		}
		catch (ConfigurationException e) {logger.error(e);}
		c.setThrowExceptionOnMissing(true);
		return c;
	}
	
	public static Configuration load(String xmlFileName)
	{
		add(xmlFileName);
		return init();
	}
	
	public synchronized static void update(String key, String value)
	{
		try
		{
			xmlSave.setProperty(key, value);
			xmlSave.save();
		}
		catch (ConfigurationException e) {logger.error(e);}
	}
	
	public static Properties loadProperties(String fileName)
	{
		Properties p = new Properties();
		File propFile = new File(fileName);
		if(propFile.exists())
		{
			logger.info("Lade Properties von "+propFile.getAbsolutePath());
			try{p.load(new FileInputStream(propFile));}
			catch (IOException e) {logger.fatal("IOException", e);}
		}
		else
		{
			int sec=15;
			logger.fatal("Properties file "+propFile.getAbsolutePath()+" does not exist!");
			logger.fatal("Shutting down application in "+sec+" seconds.");
			try {Thread.sleep(sec*1000);}
			catch (InterruptedException e) {logger.error(e);}
			System.exit(-1);
		}
		return p;
	}
	
	private static Typ getTyp(String configName)
	{
		Typ typ = Typ.UNKNOWN;
		if(configName.endsWith(".xml")){typ=Typ.XML;}
		else if(configName.endsWith(".properties")){typ=Typ.PROPERTIES;}
		return typ;
	}
}