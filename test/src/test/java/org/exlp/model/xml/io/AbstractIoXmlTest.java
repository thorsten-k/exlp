package org.exlp.model.xml.io;

import java.nio.file.Paths;

import org.exlp.test.AbstractExlpXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractIoXmlTest <T extends Object> extends AbstractExlpXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractIoXmlTest.class);
	
	public AbstractIoXmlTest(Class<T> cXml)
   	{
   		super(cXml,Paths.get("io"));
   	}
}