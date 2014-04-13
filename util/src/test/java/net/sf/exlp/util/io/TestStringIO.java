package net.sf.exlp.util.io;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.test.AbstractExlpTst;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStringIO extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestStringIO.class);
    
	private File outputFile;
	
	@Before
	public void init() throws IOException
	{
		outputFile = new File(fTarget,"hash");
		StringIO.writeTxt(TestHashUtil.inputString, outputFile);
	}
	
    @Test
    public void writeTxtIfDiffers() throws IOException, InterruptedException 
    {
    	long firstModified = outputFile.lastModified();
    	Assert.assertEquals(TestHashUtil.expected, HashUtil.hash(outputFile));
    	
    	Thread.sleep(1100);
    	
    	StringIO.writeTxtIfDiffers(TestHashUtil.inputString, outputFile);
    	Assert.assertEquals(TestHashUtil.expected, HashUtil.hash(outputFile));
    	Assert.assertEquals(firstModified, outputFile.lastModified());
    	
    	Thread.sleep(1100);
    	StringIO.writeTxtIfDiffers("test", outputFile);
    	Assert.assertNotEquals(TestHashUtil.expected, HashUtil.hash(outputFile));
    	Assert.assertTrue("First:"+firstModified+" Last:"+outputFile.lastModified(),firstModified<outputFile.lastModified());
    }
   
}