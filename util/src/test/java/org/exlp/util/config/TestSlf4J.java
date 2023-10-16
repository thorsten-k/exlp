package org.exlp.util.config;

import java.io.FileNotFoundException;

import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSlf4J extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSlf4J.class);
	    
    @Test
    public void debug() throws FileNotFoundException
    {
    	logger.debug("DEBUG");
    }
    
    @Test
    public void info() throws FileNotFoundException
    {
    	logger.info("INFO");
    }
    
    @Test
    public void error() throws FileNotFoundException
    {
    	logger.error("This is a test for logging an ERROR");
    }
    
    @Test
    public void withException() throws FileNotFoundException
    {
    	Exception e = new Exception("test");
//    	logger.error("Something went wrong ...",e);
    	logger.info("Something went wrong ...",e);
    }
    
    @Disabled
    @Test
    public void level()
    {
    	Assertions.assertEquals(false, logger.isTraceEnabled());
    	Assertions.assertEquals(false, logger.isDebugEnabled());
    	Assertions.assertEquals(false, logger.isInfoEnabled());
    	Assertions.assertEquals(false, logger.isWarnEnabled());
    	Assertions.assertEquals(true,  logger.isErrorEnabled());
    }
}