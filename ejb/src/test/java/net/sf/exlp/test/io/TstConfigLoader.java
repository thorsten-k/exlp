package net.sf.exlp.test.io;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.log.LoggerInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstConfigLoader
{
	final static Logger logger = LoggerFactory.getLogger(TstConfigLoader.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("resources/config");
			loggerInit.init();
			
		logger.debug("Test");
		
		ConfigLoader.addString("resources/config/exlp1.xml");
		Configuration config = ConfigLoader.init();
		
		int anzFiles = config.getStringArray("files/file").length;
		logger.debug("Files: "+anzFiles);
		for(int i=1;i<=anzFiles;i++)
		{
			String fileName = config.getString("files/file["+i+"]");
			logger.debug("\t"+fileName);
		}
	}
}
