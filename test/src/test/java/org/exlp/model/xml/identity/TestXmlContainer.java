package org.exlp.model.xml.identity;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlContainer extends AbstractIdentityXmlTest<IdentityContainer>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlContainer.class);
		
	public static TestXmlContainer instance() {return new TestXmlContainer();}
	private TestXmlContainer() {super(IdentityContainer.class);}
    
	@Override public IdentityContainer build(boolean wChildren)
    {
    	IdentityContainer xml = new IdentityContainer();
    	
    	if(wChildren)
    	{
    		xml.getCertificate().add(TestXmlCertificate.instance().build(false));
    		xml.getUser().add(TestXmlUser.instance().build(false));
    	}
    	
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestXmlContainer.instance().saveReferenceXml();
    }
}