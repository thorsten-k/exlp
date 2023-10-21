package de.kisner.exlp.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpMavenTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpMavenTestBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.path("exlp/maven/config.test");
		loggerInit.init();
	}
}