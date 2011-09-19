package net.sf.exlp.xml.identity;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUser extends AbstractIdentityXmlTest
{
	static Log logger = LogFactory.getLog(TestUser.class);
		
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"user.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	User test = createUser();
    	User ref = (User)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), User.class);
    	assertJaxbEquals(ref, test);
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	User xml = createUser();
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fXml, xml, getNsPrefixMapper(), true);
    }
    
    public static User createUser()
    {
    	User xml = new User();
    	xml.setFirstName("myFirst");
    	xml.setLastName("myLast");
    	xml.setAccount("myAcc");
    	xml.setEmail("my@e.mail");
    	xml.setInfo("myInfo");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestUser.initFiles();	
		TestUser test = new TestUser();
		test.save();
    }
}