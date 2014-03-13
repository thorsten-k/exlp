package net.sf.exlp.util.io;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.test.AbstractExlpTst;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFileIO extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestFileIO.class);
    
	private File f;
	private static String s = "This is a simple hash test";
	
	@Before
	public void init() throws IOException
	{
		f = new File(fTarget,"hash");
		StringIO.writeTxt(f, s);
	}
	
    @Test
    public void testFileFileHash() throws IOException 
    {
    	String hash = FileIO.getHash(f);
    	Assert.assertEquals("5056b09f63be4ef1f44e97df450151d1", hash);
    }
   
    @Test(expected=IOException.class)
    public void testFileNotFound() throws IOException 
    {
    	FileIO.getHash(new File(f,"skdjvnjkdsv"));
    }
}