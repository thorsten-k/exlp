package net.sf.exlp.util;

import java.io.IOException;

import junit.framework.Assert;
import net.sf.exlp.test.AbstractExlpTst;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDateUtil extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestDateUtil.class);
   
    @Test
    public void midnightBeginOfMonth() throws IOException 
    {
    	DateTime now = new DateTime();
    	DateTime actual = new DateTime(DateUtil.midnightBeginOfMonth(now.toDate()));
    	
    	Assert.assertEquals(now.year(), actual.year());
    	Assert.assertEquals(now.monthOfYear(), actual.monthOfYear());
    	Assert.assertEquals(1, actual.getDayOfMonth());
    	Assert.assertEquals(0, actual.getHourOfDay());
    	Assert.assertEquals(0, actual.getMinuteOfHour());
    	Assert.assertEquals(0, actual.getSecondOfMinute());
    	Assert.assertEquals(0, actual.getMillisOfSecond());
    	
    	logger.debug(actual.toString());
    }
    
    @Test
    public void midnightEndOfMonth() throws IOException 
    {
    	DateTime now = new DateTime();
    	DateTime actual = new DateTime(DateUtil.midnightEndOfMonth(now.toDate()));
    	
    	Assert.assertEquals(now.plusMonths(1).getYear(), actual.getYear());
    	Assert.assertEquals(now.plusMonths(1).getMonthOfYear(), actual.getMonthOfYear());
    	Assert.assertEquals(1, actual.getDayOfMonth());
    	Assert.assertEquals(0, actual.getHourOfDay());
    	Assert.assertEquals(0, actual.getMinuteOfHour());
    	Assert.assertEquals(0, actual.getSecondOfMinute());
    	Assert.assertEquals(0, actual.getMillisOfSecond());
    	
    	logger.debug(actual.toString());
    }
}