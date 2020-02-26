package de.kisner.exlp.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpCoreBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpCoreBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("exlp/core/config");
		loggerInit.init();
	}
}