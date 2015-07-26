package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestAcl extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestAcl.class);
		
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
    	xml.setDescription("myDescription");
    	xml.setPermission("r");
    	xml.setIo(true);
    	xml.setCi(true);
    	xml.setOi(true);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		ExlpTstBootstrap.init();
			
		TestAcl.initFiles();	
		TestAcl test = new TestAcl();
		test.save();
    }
}