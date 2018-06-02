package net.sf.exlp.util.io;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.test.AbstractExlpTst;

public class TestStringUtil extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestStringUtil.class);
    	
    @Test
    public void writeTxtIfDiffers() throws IOException, InterruptedException 
    {
    	String original = "54020201118";
    	String expected = "540202010118";
    	Assert.assertEquals(expected, StringUtil.insertAtNaturalPosition(original, 9, "0"));
    }
   
}