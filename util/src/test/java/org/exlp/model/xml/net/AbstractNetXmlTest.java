package org.exlp.model.xml.net;

import org.exlp.test.AbstractExlpTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNetXmlTest extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractNetXmlTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/net";
	
	protected static java.io.File fXml;
}