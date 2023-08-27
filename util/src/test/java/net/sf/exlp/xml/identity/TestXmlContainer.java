package net.sf.exlp.xml.identity;

import java.io.FileNotFoundException;

import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.exlp.model.xml.identity.IdentityContainer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlContainer extends AbstractIdentityXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlContainer.class);
		
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
    		xml.getCertificate().add(TestXmlCertificate.create());
    		xml.getUser().add(TestXmlUser.createUser());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		ExlpTstBootstrap.init();
			
		TestXmlContainer.initFiles();	
		TestXmlContainer test = new TestXmlContainer();
		test.save();
    }
}