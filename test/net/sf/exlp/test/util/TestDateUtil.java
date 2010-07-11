package net.sf.exlp.test.util;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.DateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDateUtil
{
	static Log logger = LogFactory.getLog(TestDateUtil.class);
	
	
	public TestDateUtil()
	{

	}
	
	public void xmlGc()
	{
		Date now = new Date();
		XMLGregorianCalendar xmlGcNow = DateUtil.getXmlGc4D(now);
		Date nowBack = DateUtil.getDate4XmlGc(xmlGcNow);
		
		logger.debug(now);
		logger.debug(xmlGcNow);
		logger.debug(nowBack);
	}

	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestDateUtil test = new TestDateUtil();
		test.xmlGc();
	}
}