package net.sf.exlp.xml.identity;

import java.io.FileNotFoundException;

import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.exlp.model.xml.identity.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUser extends AbstractIdentityXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
		
	@BeforeClass
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
		ExlpTstBootstrap.init();
			
		TestXmlUser.initFiles();	
		TestXmlUser test = new TestXmlUser();
		test.save();
    }
}