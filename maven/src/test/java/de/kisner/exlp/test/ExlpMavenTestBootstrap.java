package de.kisner.exlp.test;

import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpMavenTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpMavenTestBootstrap.class);
	
	private static final String LOG4J2_NAME = "exlp/system/io/log/maven.log4j2.xml";
	
	public static void init()
	{
		Configurator.initialize(null, LOG4J2_NAME);
		logger.info("Log4j2 configured with ["+LOG4J2_NAME+"]");
	}
}