package org.exlp.model.xml.io;

import java.io.FileNotFoundException;

import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlData extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlData.class);
	
	@BeforeAll public static void initFiles(){fXml = new java.io.File(rootDir,Data.class.getSimpleName()+".xml");}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Data test = create(true);
    	Data ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Data.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Data create(boolean withChilds)
    {
    	Data xml = new Data();
    	xml.setValue(new byte[] {1,3,43});    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
			
		TestXmlData.initFiles();	
		TestXmlData test = new TestXmlData();
		test.save();
    }
}