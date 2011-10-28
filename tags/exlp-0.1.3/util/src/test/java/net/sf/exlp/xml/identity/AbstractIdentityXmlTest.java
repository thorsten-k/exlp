package net.sf.exlp.xml.identity;

import net.sf.exlp.test.AbstractExlpTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractIdentityXmlTest extends AbstractExlpTest
{
	static Log logger = LogFactory.getLog(AbstractIdentityXmlTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/identity";
	
	protected static java.io.File fXml;
}