package net.sf.exlp.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.tree.NodeCombiner;
import org.apache.commons.configuration2.tree.UnionCombiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import net.sf.exlp.util.io.ExlpCentralConfigPointer;

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
	
	
	public void add(Path path)
	{
		configurations.add(path.toFile().getAbsolutePath());
	}
	public void addS(String s)
	{
		configurations.add(s);
	}
	
	public org.apache.commons.configuration2.Configuration combine()
	{
		NodeCombiner combiner = new UnionCombiner();
		combiner.addListNode("table");  // mark table as list node
		
		Parameters params = new Parameters();
		
		CombinedConfiguration cc = new CombinedConfiguration(combiner);
		
		for(String configName : configurations)
		{
			if(getTyp(configName).equals(Typ.XML))
			{
				FileBasedConfigurationBuilder<org.apache.commons.configuration2.XMLConfiguration> builder1 =
					    new FileBasedConfigurationBuilder<org.apache.commons.configuration2.XMLConfiguration>(org.apache.commons.configuration2.XMLConfiguration.class)
					    .configure(params.xml().setFileName(configName));
				try {
					cc.addConfiguration(builder1.getConfiguration());
				} catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return cc;
	}
	
	
	public static void add(File f)
	{
		ConfigLoader.add(f.getAbsolutePath());
	}
	
	public synchronized static void add(String configName)
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
}