package org.exlp.model.xml.io;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlData extends AbstractIoXmlTest<Data>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlData.class);
	
	public static TestXmlData instance() {return new TestXmlData();}
	private TestXmlData() {super(Data.class);}
    
    @Override public Data build(boolean wChildren)
    {
    	Data xml = new Data();
    	xml.setValue(new byte[] {1,3,43});    	
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestXmlData.instance().saveReferenceXml();
    }
}