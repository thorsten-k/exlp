package net.sf.exlp.xml.identity;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCertificate extends AbstractIdentityXmlTest
{
	static Log logger = LogFactory.getLog(TestCertificate.class);
		
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"certificate.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Certificate test = createCertificate();
    	Certificate ref = (Certificate)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Certificate.class);
    	assertJaxbEquals(ref, test);
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Certificate xml = createCertificate();
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fXml, xml, getNsPrefixMapper(), true);
    }
    
    public static Certificate createCertificate()
    {
    	Certificate xml = new Certificate();
    	xml.setSerial(1);
    	xml.setNotafter(getXmlDate());
    	xml.setCn("myCn");
    	xml.setEmail("my@e.mail");
    	return xml;
    }
	
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