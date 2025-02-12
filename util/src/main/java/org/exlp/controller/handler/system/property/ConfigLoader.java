package org.exlp.controller.handler.system.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class ConfigLoader
{
	final static Marker fatal = MarkerFactory.getMarker("FATAL");
	final static Logger logger = LoggerFactory.getLogger(ExlpCentralConfigPointer.class);
	
	private static enum Typ{UNKNOWN,XML,PROPERTIES}
	
	private static CompositeConfiguration c;
	
	private static XMLConfiguration xmlSave;
	private static ArrayList<String> alConfigNames;
	
	private final List<String> configurations;
	
	public static ConfigLoader instance()
	{
		return new ConfigLoader();
	}
	private ConfigLoader()
	{
		configurations = new ArrayList<>();
	}
	
	
	public ConfigLoader add(Path path)
	{
		if(Objects.isNull(path)) {logger.warn("Requested an additional config, but null provided");}
		else
		{
			logger.info("Adding "+path.toString());
			configurations.add(path.toFile().getAbsolutePath());
		}
		return this;
	}
	public ConfigLoader add(String s)
	{
		logger.info("Adding "+s);
		configurations.add(s);
		return this;
	}
	
	public static org.exlp.interfaces.system.property.Configuration wrap(org.apache.commons.configuration.Configuration config) {return ConfigLoader.instance().new ConfigWrapper(config);}
	public org.exlp.interfaces.system.property.Configuration wrap()
	{
		try {return new ConfigWrapper(combine());}
		catch (ConfigurationException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public Configuration combine() throws ConfigurationException
	{
		CompositeConfiguration c = new CompositeConfiguration();
		c.setThrowExceptionOnMissing(true);
		for(String configName : configurations)
		{
			switch(getTyp(configName))
			{
				case XML:	XMLConfiguration xCnf = new XMLConfiguration(configName);
							if(xmlSave==null){xmlSave=xCnf;}
							c.addConfiguration(xCnf);break;
				case PROPERTIES:	PropertiesConfiguration pCnf = new PropertiesConfiguration(configName);
									c.addConfiguration(pCnf);break;
				default: logger.warn("Unknwon Resource: "+configName);break;
			}
		}
		return c;
	}

	
	public static void addFile(File f)
	{
		ConfigLoader.addString(f.getAbsolutePath());
	}
	
	public static void addString(String configName)
	{
		if(alConfigNames==null){alConfigNames = new ArrayList<String>();}
		
		logger.info("Adding config from "+configName);
		alConfigNames.add(configName);
	}
	
	public synchronized static Configuration init()
	{
		try
		{
			return initWithException();
		}
		catch (ConfigurationException e) {logger.error("",e);}
		return null;
	}
	
	public synchronized static Configuration initWithException() throws ConfigurationException
	{
		c = new CompositeConfiguration();
		c.setThrowExceptionOnMissing(true);
		for(String configName : alConfigNames)
		{
			switch(getTyp(configName))
			{
				case XML:	XMLConfiguration xCnf = new XMLConfiguration(configName);
							if(xmlSave==null){xmlSave=xCnf;}
							c.addConfiguration(xCnf);break;
				case PROPERTIES:	PropertiesConfiguration pCnf = new PropertiesConfiguration(configName);
									c.addConfiguration(pCnf);break;
				default: logger.warn("Unknwon Resource: "+configName);break;
			}
		}
		return c;
	}
	
	public static Configuration load(String xmlFileName)
	{
		addString(xmlFileName);
		return init();
	}
	
	public synchronized static void update(String key, String value)
	{
		try
		{
			xmlSave.setProperty(key, value);
			xmlSave.save();
		}
		catch (ConfigurationException e) {logger.error("",e);}
	}
	
	public static Properties loadProperties(String fileName)
	{
		Properties p = new Properties();
		File propFile = new File(fileName);
		if(propFile.exists())
		{
			logger.info("Lade Properties von "+propFile.getAbsolutePath());
			try{p.load(new FileInputStream(propFile));}
			catch (IOException e) {logger.error("IOException", e);}
		}
		else
		{
			int sec=15;
			logger.error(fatal,"Properties file "+propFile.getAbsolutePath()+" does not exist!");
			logger.error(fatal,"Shutting down application in "+sec+" seconds.");
			try {Thread.sleep(sec*1000);}catch (InterruptedException e) {logger.error("",e);}
			System.exit(-1);
		}
		return p;
	}
	
	private static Typ getTyp(String configName)
	{
		Typ typ = Typ.UNKNOWN;
		if(configName.endsWith(".xml")){typ=Typ.XML;}
		else if(configName.endsWith(".properties")){typ=Typ.PROPERTIES;}
		else if(configName.endsWith(".txt")){typ=Typ.PROPERTIES;}
		return typ;
	}
	
	private class ConfigWrapper implements org.exlp.interfaces.system.property.Configuration
	{
		private org.apache.commons.configuration.Configuration config;
		
		public ConfigWrapper(org.apache.commons.configuration.Configuration config)
		{
			this.config=config;
		}

		@Override public String getString(String key) {return config.getString(key);}
		@Override public String getString(String key, String fallback) {return config.getString(key,fallback);}
		@Override public int getInt(String key) {return config.getInt(key);}
		@Override public int getInt(String key, int fallback) {return config.getInt(key,fallback);}
		@Override public Boolean getBoolean(String key) {return config.getBoolean(key);}
		@Override public Boolean getBoolean(String key, Boolean fallback) {return config.getBoolean(key,fallback);}
	}
}