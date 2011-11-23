package net.sf.exlp.test;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExlpTstBootstrap
{
	static Log logger = LogFactory.getLog(ExlpTstBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("exlp-util.test");
		loggerInit.init();
	}
}