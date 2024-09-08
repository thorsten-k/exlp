package org.exlp.model.xml.io;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHash extends AbstractIoXmlTest<Hash>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHash.class);
	
	public static TestXmlHash instance() {return new TestXmlHash();}
	private TestXmlHash() {super(Hash.class);}
    
    @Override public Hash build(boolean wChildren)
    {
    	Hash xml = new Hash();
    	xml.setValue("myValue");

    	if(wChildren)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestXmlHash.instance().saveReferenceXml();
    }
}