package org.exlp.util.io;

import java.io.File;
import java.io.IOException;

import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.HashUtil;
import net.sf.exlp.util.io.StringIO;

public class TestHashUtil extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestHashUtil.class);
    
	public static String expected = "5056b09f63be4ef1f44e97df450151d1";
	public static String inputString = "This is a simple hash test";
	
	private byte[] inputByte;
	private File inputFile;
	
	@BeforeEach
	public void init() throws IOException
	{
		inputByte = inputString.getBytes();
		
		inputFile = new File(fTarget,"hash");
		StringIO.writeTxt(inputFile, inputString);
	}
	
    @Test
    public void hashString() throws IOException 
    {
    	Assertions.assertEquals(expected, HashUtil.hash(inputString));
    }
	
    @Test
    public void hashByte() throws IOException 
    {
    	Assertions.assertEquals(expected, HashUtil.hash(inputByte));
    }
  
    @Test
    public void hashFile() throws IOException 
    {
    	Assertions.assertEquals(expected, HashUtil.hash(inputFile));
    }
    
    @Test
    public void fileNotFound()
    {
    	Assertions.assertThrows(IOException.class, () -> {HashUtil.hash(new File(fTarget,"skdjvnjkdsv"));}, "IOException was expected");
//		Assertions.assertEquals("For input string: \"One\"", thrown.getMessage());
    }
    
//    @Test
//    public void numberFormat()
//    {
//	    NumberFormatException thrown = Assertions.assertThrows(NumberFormatException.class, () -> {
//			Integer.parseInt("One");
//		}, "NumberFormatException was expected");
//		
//		Assertions.assertEquals("For input string: \"One\"", thrown.getMessage());
//    }
}