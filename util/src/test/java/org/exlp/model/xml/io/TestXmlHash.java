package org.exlp.model.xml.io;

import java.io.FileNotFoundException;

import org.exlp.model.xml.io.Hash;
import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlHash extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHash.class);
	
	@BeforeAll
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,Hash.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Hash test = create(true);
    	Hash ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Hash.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Hash create(boolean withChilds)
    {
    	Hash xml = new Hash();
    	xml.setValue("myValue");

    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
			
		TestXmlHash.initFiles();	
		TestXmlHash test = new TestXmlHash();
		test.save();
    }
}