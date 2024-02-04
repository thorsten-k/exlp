package net.sf.exlp.test;

import org.exlp.util.io.log.LoggerInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpShellTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpShellTestBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.path("config.exlp-shell.test");
			loggerInit.init();
	}
}