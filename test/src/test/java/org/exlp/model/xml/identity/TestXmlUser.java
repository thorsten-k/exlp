package org.exlp.model.xml.identity;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUser extends AbstractIdentityXmlTest<User>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
		
	public static TestXmlUser instance() {return new TestXmlUser();}
	private TestXmlUser() {super(User.class);}
    
    @Override public User build(boolean wChildren)
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
		ExlpTestBootstrap.init();
		TestXmlUser.instance().saveReferenceXml();
    }
}