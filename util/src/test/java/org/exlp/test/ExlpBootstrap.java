package org.exlp.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.jx.ExlpNsPrefixMapper;
import org.exlp.util.jx.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpBootstrap.class);
	
	public enum System{exlp}
	
	public static void init()
	{
		LoggerBootstrap.instance("cli.util.log4j2.xml").path("exlp/system/io/log").init();
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());
	}
}