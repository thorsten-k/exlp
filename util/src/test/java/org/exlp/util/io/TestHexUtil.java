package org.exlp.util.io;

import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHexUtil extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestHexUtil.class);
    
	public static void main(String args[]) throws Exception
	{
		ExlpBootstrap.init();
		
	    byte[] byteArray = {(byte)0, (byte)255, (byte)111};

	    System.out.println(HexUtil.getHexString(byteArray));
	}
}