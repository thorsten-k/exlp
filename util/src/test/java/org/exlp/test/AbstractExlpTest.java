package org.exlp.test;

import java.io.File;
import java.time.LocalDateTime;

import javax.xml.datatype.XMLGregorianCalendar;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.ExlpNsPrefixMapper;
import org.exlp.util.jx.JaxbUtil;
import org.exlp.util.system.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpTest.class);
	
	protected static File fTarget;
	
	@BeforeAll
	public static void initFile()
	{
		if(!LoggerInit.isLog4jInited()){initLogger();}
		String dirTarget = System.getProperty("targetDir");
		if(dirTarget==null){dirTarget="target";}
		setfTarget(new File(dirTarget));
		logger.debug("Using targeDir "+fTarget.getAbsolutePath());
	}
	protected static void setfTarget(File fTarget) {AbstractExlpTest.fTarget = fTarget;}
	
	@BeforeAll
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerBootstrap.instance("unit.util.log4j2.xml").path("exlp/system/io/log").init();
		}
    }
	
	@BeforeAll
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());
	}
	
	
	protected void assertJaxbEquals(Object ref, Object test)
	{
		Assertions.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
	}
	
	protected static XMLGregorianCalendar getXmlDate()
	{
		LocalDateTime ldt = LocalDateTime.of(2011,11,11,11,11,11);
		return DateUtil.toXmlGc(ldt);
	}
	
	protected void save(Object xml, File f)
	{
		logger.debug("Saving Reference XML");
		JaxbUtil.debug(xml);
    	JaxbUtil.save(f, xml, true);
	}
}