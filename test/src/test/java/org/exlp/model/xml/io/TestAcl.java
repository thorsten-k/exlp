package org.exlp.model.xml.io;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAcl extends AbstractIoXmlTest<Acl>
{
	final static Logger logger = LoggerFactory.getLogger(TestAcl.class);
		
	public static TestAcl instance() {return new TestAcl();}
	private TestAcl() {super(Acl.class);}
        
    @Override public Acl build(boolean wChildren)
    {
    	Acl xml = new Acl();
    	xml.setId(1l);
    	xml.setDescription("myDescription");
    	xml.setPermission("r");
    	xml.setIo(true);
    	xml.setCi(true);
    	xml.setOi(true);
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestAcl.instance().saveReferenceXml();
    }
}