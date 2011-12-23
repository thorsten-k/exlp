package net.sf.exlp.xml.io;

import net.sf.exlp.test.AbstractExlpTst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractIoXmlTest extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractIoXmlTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/io";
	
	protected static java.io.File fXml;
}