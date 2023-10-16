package org.exlp.util.io;

import java.io.File;
import java.io.IOException;

import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.HashUtil;
import net.sf.exlp.util.io.StringIO;

public class TestStringIO extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestStringIO.class);
    
	private File outputFile;
	
	@BeforeEach
	public void init() throws IOException
	{
		outputFile = new File(fTarget,"hash");
		StringIO.writeTxt(TestHashUtil.inputString, outputFile);
	}
	
    @Test
    public void writeTxtIfDiffers() throws IOException, InterruptedException 
    {
    	long firstModified = outputFile.lastModified();
    	Assertions.assertEquals(TestHashUtil.expected, HashUtil.hash(outputFile));
    	
    	Thread.sleep(1100);
    	
    	StringIO.writeTxtIfDiffers(TestHashUtil.inputString, outputFile);
    	Assertions.assertEquals(TestHashUtil.expected, HashUtil.hash(outputFile));
    	Assertions.assertEquals(firstModified, outputFile.lastModified());
    	
    	Thread.sleep(1100);
    	StringIO.writeTxtIfDiffers("test", outputFile);
    	Assertions.assertNotEquals(TestHashUtil.expected, HashUtil.hash(outputFile));
    	Assertions.assertTrue(firstModified<outputFile.lastModified(),"First:"+firstModified+" Last:"+outputFile.lastModified());
    }
   
    public static void main(String[] args) throws Exception
   	{
   		ExlpBootstrap.init();
   		
   		TestStringIO test = new TestStringIO();
   		test.init(); test.writeTxtIfDiffers();
   	}
}