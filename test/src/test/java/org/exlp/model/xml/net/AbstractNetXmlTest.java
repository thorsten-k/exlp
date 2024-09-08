package org.exlp.model.xml.net;

import java.nio.file.Paths;

import org.exlp.test.AbstractExlpXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNetXmlTest <T extends Object> extends AbstractExlpXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractNetXmlTest.class);
	
	public AbstractNetXmlTest(Class<T> cXml)
   	{
   		super(cXml,Paths.get("net"));
   	}
}