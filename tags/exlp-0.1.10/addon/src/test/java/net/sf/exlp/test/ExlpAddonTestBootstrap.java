package net.sf.exlp.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpAddonTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpAddonTestBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("src/test/resources/exlp-addon.test");
			loggerInit.init();
	}
}