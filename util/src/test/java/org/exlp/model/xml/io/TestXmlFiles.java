package org.exlp.model.xml.io;

import java.io.FileNotFoundException;

import org.exlp.test.ExlpBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFiles extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFiles.class);
	
	@BeforeAll public static void initFiles(){fXml = new java.io.File(rootDir,Files.class.getSimpleName()+".xml");}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Files test = create(true);
    	Files ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Files.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Files create(boolean withChilds)
    {
    	Files xml = new Files();
    	xml.getFile().add(TestXmlFile.create(false));
    	xml.getFile().add(TestXmlFile.create(false));
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
			
		TestXmlFiles.initFiles();	
		TestXmlFiles test = new TestXmlFiles();
		test.save();
    }
}