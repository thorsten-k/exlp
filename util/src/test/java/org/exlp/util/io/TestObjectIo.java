package org.exlp.util.io;

import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.ObjectIO;

public class TestObjectIo extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestObjectIo.class);
	
	public static void main(String args[])
	{
		ExlpBootstrap.init();
	
		String s1 = "Hallo";
		String s2 = "Hallo";
		
		logger.debug(ObjectIO.printByteStream(ObjectIO.hash(s1),true));
		logger.debug(ObjectIO.printByteStream(ObjectIO.hash(s2),true));

	}
}