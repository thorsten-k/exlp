package net.sf.exlp.xml.identity;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContainer extends AbstractIdentityXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestContainer.class);
		
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"container.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	IdentityContainer test = create();
    	IdentityContainer ref = (IdentityContainer)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), IdentityContainer.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static IdentityContainer create(){return create(true);}
    public static IdentityContainer create(boolean withChilds)
    {
    	IdentityContainer xml = new IdentityContainer();
    	
    	if(withChilds)
    	{
    		xml.getCertificate().add(TestCertificate.create());
    		xml.getUser().add(TestUser.createUser());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
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