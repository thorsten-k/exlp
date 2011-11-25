package net.sf.exlp.xml.identity;

import net.sf.exlp.test.AbstractExlpTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractIdentityXmlTest extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractIdentityXmlTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/identity";
	
	protected static java.io.File fXml;
}