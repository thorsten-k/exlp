package net.sf.exlp.xml.identity;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCertificate extends AbstractIdentityXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestCertificate.class);
		
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"certificate.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Certificate test = create();
    	Certificate ref = (Certificate)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Certificate.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Certificate create() {return create(true);}
    public static Certificate create(boolean withChilds)
    {
    	Certificate xml = new Certificate();
    	xml.setId(123);
    	xml.setSerial(1);
    	xml.setNotAfter(getXmlDate());
    	xml.setNotBefore(getXmlDate());
    	xml.setCn("myCn");
    	xml.setEmail("my@e.mail");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestCertificate.initFiles();	
		TestCertificate test = new TestCertificate();
		test.save();
    }
}