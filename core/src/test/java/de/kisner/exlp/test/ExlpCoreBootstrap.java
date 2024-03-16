package de.kisner.exlp.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpCoreBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpCoreBootstrap.class);
	
	public static void init()
	{
		LoggerBootstrap.instance("core.log4j2.xml").path("jeesl/system/io/log").init();
	}
}