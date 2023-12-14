package org.exlp.test;

import java.io.File;
import java.time.LocalDateTime;

import javax.xml.datatype.XMLGregorianCalendar;

import org.exlp.util.jx.ExlpNsPrefixMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

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
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.path("exlp/test/config");
			loggerInit.init();
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