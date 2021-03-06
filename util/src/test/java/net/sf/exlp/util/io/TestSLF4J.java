package net.sf.exlp.util.io;

import java.io.FileNotFoundException;

import net.sf.exlp.test.AbstractExlpTst;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSLF4J extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestSLF4J.class);
	    
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
    
    @Test
    public void level()
    {
    	Assert.assertEquals(false, logger.isTraceEnabled());
    	Assert.assertEquals(false, logger.isDebugEnabled());
    	Assert.assertEquals(false, logger.isInfoEnabled());
    	Assert.assertEquals(false, logger.isWarnEnabled());
    	Assert.assertEquals(true,  logger.isErrorEnabled());
    }
}