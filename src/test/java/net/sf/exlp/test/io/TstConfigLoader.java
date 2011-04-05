package net.sf.exlp.test.io;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstConfigLoader
{
	static Log logger = LogFactory.getLog(TstConfigLoader.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		logger.debug("Test");
		
		ConfigLoader.add("resources/config/exlp1.xml");
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
