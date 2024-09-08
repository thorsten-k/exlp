package org.exlp.model.xml.identity;

import java.nio.file.Paths;

import org.exlp.test.AbstractExlpXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractIdentityXmlTest <T extends Object> extends AbstractExlpXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractIdentityXmlTest.class);
	
	public AbstractIdentityXmlTest(Class<T> cXml)
   	{
   		super(cXml,Paths.get("identity"));
   	}
}