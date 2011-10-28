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
    public void testFile() throws FileNotFoundException
    {
    	Acl test = createAcl();
    	Acl ref = (Acl)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Acl.class);
    	assertJaxbEquals(ref, test);
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Acl xml = createAcl();
    	JaxbUtil.debug2(this.getClass(),xml, getNsPrefixMapper());
    	JaxbUtil.save(fXml, xml, getNsPrefixMapper(), true);
    }
    
    public static Acl createAcl()
    {
    	Acl xml = new Acl();
    	xml.setId(1);
    	xml.setPermission("r");
    	xml.setPass(true);
    	return xml;
    }
	
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