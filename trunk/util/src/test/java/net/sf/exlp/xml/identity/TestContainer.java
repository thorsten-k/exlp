package net.sf.exlp.xml.identity;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestContainer extends AbstractIdentityXmlTest
{
	static Log logger = LogFactory.getLog(TestContainer.class);
		
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"container.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	IdentityContainer test = createIdentityContainer();
    	IdentityContainer ref = (IdentityContainer)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), IdentityContainer.class);
    	assertJaxbEquals(ref, test);
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	IdentityContainer xml = createIdentityContainer();
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fXml, xml, getNsPrefixMapper(), true);
    }
    
    public static IdentityContainer createIdentityContainer()
    {
    	IdentityContainer xml = new IdentityContainer();
    	
    	xml.getCertificate().add(TestCertificate.createCertificate());
    	xml.getUser().add(TestUser.createUser());
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestContainer.initFiles();	
		TestContainer test = new TestContainer();
		test.save();
    }
}