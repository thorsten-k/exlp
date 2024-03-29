package org.exlp.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;

import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.exlp.util.system.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDateUtil extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestDateUtil.class);
   
    @Test
    public void dummy() throws DatatypeConfigurationException
    {
    	Assertions.assertTrue(true);
    }
    
    public void xmlGc() throws DatatypeConfigurationException
    {
    	Date d = new Date();
    	
    	XMLGregorianCalendar x1 = DateUtil.toXmlGc(d);
    	XMLGregorianCalendar x2 = DateUtil.toXmlGc(d);
    	
    	logger.info("x1 to "+x1);
    	logger.info("x2 get "+x2);
    	
    	String s = "2022-08-05T20:00";
    	XMLGregorianCalendar x3 = DatatypeFactory.newInstance().newXMLGregorianCalendar(s);
    	logger.info("x3 get "+x3);
    }
    
    public void xmlGc1()
    {
    	XMLGregorianCalendar x0 = DateUtil.toXmlGc(new Date());
    	XMLGregorianCalendar x1 = DateUtil.toXmlGc(LocalDate.now());
    	XMLGregorianCalendar x2 = DateUtil.toXmlGc(LocalDateTime.now());
    	XMLGregorianCalendar x3 = DateUtil.toXmlGc(ZonedDateTime.now());
    	XMLGregorianCalendar x4 = DateUtil.toXmlGc(ZonedDateTime.of(LocalDateTime.now(),ZoneId.of("UTC")));
    	XMLGregorianCalendar x5 = DateUtil.toXmlGc(ZonedDateTime.of(LocalDateTime.now(),ZoneId.of("Etc/GMT")));
    	
    	logger.info("x0 d "+x0);
    	logger.info("x1 ld "+x1);
    	logger.info("x2 ldt "+x2);
    	logger.info("x3 zdt1 "+x3);
    	logger.info("x4 zdt2 "+x4);
    	logger.info("x5 zdt3 "+x5);
    }
    
//    
//    @Test
//    public void midnightEndOfMonth() throws IOException 
//    {
//    	DateTime now = new DateTime();
//    	DateTime actual = new DateTime(DateUtil.midnightEndOfMonth(now.toDate()));
//    	
//    	Assert.assertEquals(now.plusMonths(1).getYear(), actual.getYear());
//    	Assert.assertEquals(now.plusMonths(1).getMonthOfYear(), actual.getMonthOfYear());
//    	Assert.assertEquals(1, actual.getDayOfMonth());
//    	Assert.assertEquals(0, actual.getHourOfDay());
//    	Assert.assertEquals(0, actual.getMinuteOfHour());
//    	Assert.assertEquals(0, actual.getSecondOfMinute());
//    	Assert.assertEquals(0, actual.getMillisOfSecond());
//    	
//    	logger.debug(actual.toString());
//    }
    
    public void xmlGc2()
	{
		Date now = new Date();
		XMLGregorianCalendar xmlGcNow = DateUtil.toXmlGc(now);
		Date nowBack = DateUtil.toDate(xmlGcNow);
		
		logger.debug(now.toString());
		logger.debug(xmlGcNow.toString());
		logger.debug(nowBack.toString());
	}
    
    public void instant()
    {
    	Instant instant = Instant.now();
    	LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    	
    	logger.info(instant.toString());
    	logger.info(ldt.toString());
    	
    	LocalDateTime ldtNow = LocalDateTime.now();
    	String s = ldtNow.toString();
    	logger.info("String: "+s);
    	
    	LocalDateTime ldt1 = LocalDateTime.parse("2023-08-07T15:24:18.366");
    	LocalDateTime ldt2 = LocalDateTime.parse("2023-08-07T15:24:18.0");
    	LocalDateTime ldt3 = LocalDateTime.parse("2023-08-07T15:24:18");
    	
    	logger.info(ldt1.toString());
    	logger.info(ldt2.toString());
    	logger.info(ldt3.toString());
    }
    
    public static void main(String[] args) throws ParserConfigurationException, DatatypeConfigurationException
	{
		ExlpBootstrap.init();
		
		TestDateUtil test = new TestDateUtil();
//		test.xmlGc();
//		test.xmlGc1();
		test.instant();
	}
}