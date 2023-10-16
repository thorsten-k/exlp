package org.exlp.model.xml.identity;

import java.io.FileNotFoundException;

import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlUser extends AbstractIdentityXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
		
	@BeforeAll
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"user.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	User test = createUser();
    	User ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), User.class);
    	assertJaxbEquals(ref, test);
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	User xml = createUser();
    	JaxbUtil.debug(xml);
    	JaxbUtil.save(fXml, xml, true);
    }
    
    public static User createUser()
    {
    	User xml = new User();
    	xml.setFirstName("myFirst");
    	xml.setLastName("myLast");
    	xml.setAccount("myAcc");
    	xml.setPassword("myPwd");
    	xml.setEmail("my@e.mail");
    	xml.setInfo("myInfo");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
			
		TestXmlUser.initFiles();	
		TestXmlUser test = new TestXmlUser();
		test.save();
    }
}