package org.exlp.util.io;

import java.io.IOException;

import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStringUtil extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestStringUtil.class);
    	
    @Test
    public void writeTxtIfDiffers() throws IOException, InterruptedException 
    {
    	String original = "54020201118";
    	String expected = "540202010118";
    	Assertions.assertEquals(expected, StringUtil.insertAtNaturalPosition(original, 9, "0"));
    }
}