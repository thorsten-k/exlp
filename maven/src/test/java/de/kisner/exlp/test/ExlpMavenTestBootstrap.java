package de.kisner.exlp.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpMavenTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpMavenTestBootstrap.class);
	
	public static void init()
	{
		LoggerBootstrap.instance("maven.log4j2.xml").path("exlp/system/io/log").init();
	}
}