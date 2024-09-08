package org.exlp.model.xml.io;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFiles extends AbstractIoXmlTest<Files>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFiles.class);
	
	public static TestXmlFiles instance() {return new TestXmlFiles();}
	private TestXmlFiles() {super(Files.class);}
    
    @Override public Files build(boolean wChildren)
    {
    	Files xml = new Files();
    	xml.getFile().add(TestXmlFile.instance().build(false));
    	xml.getFile().add(TestXmlFile.instance().build(false));
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestXmlFiles.instance().saveReferenceXml();
    }
}