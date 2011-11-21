package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAcl extends AbstractIoXmlTest
{
	static Log logger = LogFactory.getLog(TestAcl.class);
		
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"acl.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Acl test = create();
    	Acl ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Acl.class);
    	assertJaxbEquals(ref, test);
    }
        
    public static Acl create()
    {
    	Acl xml = new Acl();
    	xml.setId(1);
    	xml.setPermission("r");
    	xml.setPass(true);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestAcl.initFiles();	
		TestAcl test = new TestAcl();
		test.save();
    }
}