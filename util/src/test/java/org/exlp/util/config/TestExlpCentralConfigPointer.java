package org.exlp.util.config;

import java.io.FileNotFoundException;

import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;

public class TestExlpCentralConfigPointer extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExlpCentralConfigPointer.class);
    
	@Disabled
    @Test
    public void testFile() throws FileNotFoundException
    {
    	
    }
    
	
	public static void main(String[] args) throws ExlpConfigurationException
    {
		ExlpBootstrap.init();
			
//		File f = ExlpCentralConfigPointer.getFile("test");
//		logger.debug(f.getAbsolutePath());
    }
}