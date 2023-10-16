package org.exlp.model.xml.net;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlDatabase extends AbstractNetXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDatabase.class);
		
	@BeforeAll
	public static void initFiles()
	{
		fXml = new File(rootDir,Database.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Database test = create();
    	Database ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Database.class);
    	assertJaxbEquals(ref, test);
    }
        
    public static Database create()
    {
    	Database xml = new Database();
    	xml.setId(1l);
    	xml.setDatabase("myDb");
    	xml.setPassword("myPwd");
    	xml.setSchema("mySchema");
    	xml.setType("myType");
    	xml.setUser("myUser");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
			
		TestXmlDatabase.initFiles();	
		TestXmlDatabase test = new TestXmlDatabase();
		test.save();
    }
}