package net.sf.exlp.test;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExlpAddonTstBootstrap
{
	static Log logger = LogFactory.getLog(ExlpAddonTstBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("src/test/resources/exlp-addon");
			loggerInit.init();
	}
}