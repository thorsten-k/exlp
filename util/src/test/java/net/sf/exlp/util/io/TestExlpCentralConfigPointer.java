package net.sf.exlp.util.io;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.test.AbstractExlpTest;
import net.sf.exlp.util.exception.ExlpConfigurationException;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExlpCentralConfigPointer extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExlpCentralConfigPointer.class);
    
    @Test
    @Ignore
    public void testFile() throws FileNotFoundException
    {
    	
    }
    
	
	public static void main(String[] args) throws ExlpConfigurationException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		File f = ExlpCentralConfigPointer.getFile("test");
		logger.debug(f.getAbsolutePath());
    }
}