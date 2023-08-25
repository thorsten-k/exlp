package net.sf.exlp.util.io;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.test.AbstractExlpTst;
import net.sf.exlp.test.ExlpTstBootstrap;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExlpCentralConfigPointer extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestExlpCentralConfigPointer.class);
    
    @Test
    @Ignore
    public void testFile() throws FileNotFoundException
    {
    	
    }
    
	
	public static void main(String[] args) throws ExlpConfigurationException
    {
		ExlpTstBootstrap.init();
			
//		File f = ExlpCentralConfigPointer.getFile("test");
//		logger.debug(f.getAbsolutePath());
    }
}