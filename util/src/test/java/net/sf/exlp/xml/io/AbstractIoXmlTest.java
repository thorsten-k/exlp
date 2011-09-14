package net.sf.exlp.xml.io;

import net.sf.exlp.test.AbstractExlpTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractIoXmlTest extends AbstractExlpTest
{
	static Log logger = LogFactory.getLog(AbstractIoXmlTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/io";
	
	protected static java.io.File fXml;
}