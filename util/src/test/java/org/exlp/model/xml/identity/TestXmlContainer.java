package org.exlp.model.xml.identity;

import java.io.FileNotFoundException;

import org.exlp.test.ExlpBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlContainer extends AbstractIdentityXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlContainer.class);
		
	@BeforeAll
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
    		xml.getCertificate().add(TestXmlCertificate.create());
    		xml.getUser().add(TestXmlUser.createUser());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
			
		TestXmlContainer.initFiles();	
		TestXmlContainer test = new TestXmlContainer();
		test.save();
    }
}