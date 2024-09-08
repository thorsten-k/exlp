package org.exlp.model.xml.io;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPolicy extends AbstractIoXmlTest<Policy>
{
	final static Logger logger = LoggerFactory.getLogger(TestPolicy.class);
		
	public static TestPolicy instance() {return new TestPolicy();}
	private TestPolicy() {super(Policy.class);}
    
    @Override public Policy build(boolean wChildren)
    {
    	Policy xml = new Policy();
    	xml.setId(1l);
    	xml.setCode("myCode");
    	xml.setGroup("Administrators");
    	xml.setUser("Administrator");
    	xml.setDescription("myDescription");
    	xml.setName("myName");
    	
    	if(wChildren)
    	{
    		xml.getAcl().add(TestAcl.instance().build(false));
    		xml.getAcl().add(TestAcl.instance().build(false));
    	}
    	
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestPolicy.instance().saveReferenceXml();
    }
}