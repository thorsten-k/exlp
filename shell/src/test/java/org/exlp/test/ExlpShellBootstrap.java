package org.exlp.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpShellBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpShellBootstrap.class);
	
	public static void init()
	{
		LoggerBootstrap.instance("shell.log4j2.xml").path("exlp/system/io/log").init();
	}
}