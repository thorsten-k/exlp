package net.sf.exlp.test.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstDateUtil
{
	static Log logger = LogFactory.getLog(TstDateUtil.class);
	
	
	public TstDateUtil()
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
	
	public void testQuarter()
	{
		Date d = DateUtil.getDateFromInt(2000, 1, 1);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		for(int i=1;i<=12;i++)
		{
			logger.debug(DateUtil.getQuarter(gc.getTime())+" "+gc.getTime());
			gc.add(GregorianCalendar.MONTH, 1);
		}
	}

	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TstDateUtil test = new TstDateUtil();
//		test.xmlGc();
		test.testQuarter();
	}
}