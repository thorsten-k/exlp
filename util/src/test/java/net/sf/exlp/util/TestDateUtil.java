package net.sf.exlp.util;

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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.test.AbstractExlpTst;
import net.sf.exlp.test.ExlpTstBootstrap;

public class TestDateUtil extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestDateUtil.class);
   
    @Test
    public void dummy() throws DatatypeConfigurationException
    {
    	
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
    }
    
    public static void main(String[] args) throws ParserConfigurationException, DatatypeConfigurationException
	{
		ExlpTstBootstrap.init();
		
		TestDateUtil test = new TestDateUtil();
//		test.xmlGc();
//		test.xmlGc1();
		test.instant();
	}
}