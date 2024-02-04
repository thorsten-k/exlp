package org.exlp.model.xml.net;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.test.ExlpBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHost extends AbstractNetXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHost.class);
		
	@BeforeAll
	public static void initFiles()
	{
		fXml = new File(rootDir,Host.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Host test = create();
    	Host ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Host.class);
    	assertJaxbEquals(ref, test);
    }
        
    public static Host create()
    {
    	Host xml = new Host();
    	xml.setId(1l);
    	xml.setName("my.name");
    	xml.setPort(1234);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
			
		TestXmlHost.initFiles();	
		TestXmlHost test = new TestXmlHost();
		test.save();
    }
}