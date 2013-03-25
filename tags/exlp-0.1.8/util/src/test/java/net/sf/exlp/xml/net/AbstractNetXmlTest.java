package net.sf.exlp.xml.net;

import net.sf.exlp.test.AbstractExlpTst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNetXmlTest extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractNetXmlTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/net";
	
	protected static java.io.File fXml;
}