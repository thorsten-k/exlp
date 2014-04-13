package net.sf.exlp.util.io;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.test.AbstractExlpTst;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHashUtil extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestHashUtil.class);
    
	public static String expected = "5056b09f63be4ef1f44e97df450151d1";
	public static String inputString = "This is a simple hash test";
	
	private byte[] inputByte;
	private File inputFile;
	
	@Before
	public void init() throws IOException
	{
		inputByte = inputString.getBytes();
		
		inputFile = new File(fTarget,"hash");
		StringIO.writeTxt(inputFile, inputString);
	}
	
    @Test
    public void hashString() throws IOException 
    {
    	Assert.assertEquals(expected, HashUtil.hash(inputString));
    }
	
    @Test
    public void hashByte() throws IOException 
    {
    	Assert.assertEquals(expected, HashUtil.hash(inputByte));
    }
  
    @Test
    public void hashFile() throws IOException 
    {
    	Assert.assertEquals(expected, HashUtil.hash(inputFile));
    }
    
    @Test(expected=IOException.class)
    public void hasFileNotFound() throws IOException 
    {
    	HashUtil.hash(new File(fTarget,"skdjvnjkdsv"));
    }

}