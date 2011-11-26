package net.sf.exlp.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpAddonTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpAddonTstBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("src/test/resources/exlp-addon");
			loggerInit.init();
	}
}