package org.exlp.model.xml.net;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHost extends AbstractNetXmlTest<Host>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHost.class);
		
	public static TestXmlHost instance() {return new TestXmlHost();}
	private TestXmlHost() {super(Host.class);}
        
    @Override public Host build(boolean wChildren)
    {
    	Host xml = new Host();
    	xml.setId(1l);
    	xml.setName("my.name");
    	xml.setPort(1234);
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestXmlHost.instance().saveReferenceXml();
    }
}