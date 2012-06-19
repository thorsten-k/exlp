package net.sf.exlp.util.io;

import java.io.IOException;

import junit.framework.Assert;
import net.sf.exlp.test.AbstractExlpTst;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHashUtil extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestHashUtil.class);
    
	private String inputS;
	private byte[] inputB;
	
	@Before
	public void init() throws IOException
	{
		inputS = "This is a simple hash test";
		inputB = inputS.getBytes();
	}
	
    @Test
    public void testHash() throws IOException 
    {
    	Assert.assertEquals("5056b09f63be4ef1f44e97df450151d1", HashUtil.hash(inputB));
    	Assert.assertEquals("5056b09f63be4ef1f44e97df450151d1", HashUtil.hash(inputS));
    }
   
    

}