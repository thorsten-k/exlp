package de.kisner.exlp.test;

import org.exlp.util.io.log.LoggerInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpCoreBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpCoreBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.path("exlp/core/config");
		loggerInit.init();
	}
}