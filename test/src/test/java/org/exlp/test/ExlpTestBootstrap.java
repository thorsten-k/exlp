package org.exlp.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpTestBootstrap.class);
	
	public enum System {exlp}
	
	public static void init()
	{
		LoggerBootstrap.instance().path("exlp/system/io/log").init();
//		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());
	}
}