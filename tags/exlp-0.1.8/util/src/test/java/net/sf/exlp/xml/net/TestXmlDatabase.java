package net.sf.exlp.xml.net;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDatabase extends AbstractNetXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDatabase.class);
		
	@BeforeClass
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
    	xml.setId(1);
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
		ExlpTstBootstrap.init();
			
		TestXmlDatabase.initFiles();	
		TestXmlDatabase test = new TestXmlDatabase();
		test.save();
    }
}