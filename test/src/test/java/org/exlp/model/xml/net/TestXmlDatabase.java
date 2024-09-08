package org.exlp.model.xml.net;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDatabase extends AbstractNetXmlTest<Database>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDatabase.class);
		
	public static TestXmlDatabase instance() {return new TestXmlDatabase();}
	private TestXmlDatabase() {super(Database.class);}
        
    @Override public Database build(boolean wChildren)
    {
    	Database xml = new Database();
    	xml.setId(1l);
    	xml.setDatabase("myDb");
    	xml.setPassword("myPwd");
    	xml.setSchema("mySchema");
    	xml.setType("myType");
    	xml.setUser("myUser");
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestXmlDatabase.instance().saveReferenceXml();
    }
}