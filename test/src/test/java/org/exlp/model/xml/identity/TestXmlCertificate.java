package org.exlp.model.xml.identity;

import org.exlp.test.ExlpTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCertificate extends AbstractIdentityXmlTest<Certificate>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCertificate.class);

	public static TestXmlCertificate instance() {return new TestXmlCertificate();}
	private TestXmlCertificate() {super(Certificate.class);}
    
    public Certificate build(boolean wChildren)
    {
    	Certificate xml = new Certificate();
    	xml.setId(123l);
    	xml.setSerial(1);
//    	xml.setNotAfter(getXmlDate());
//    	xml.setNotBefore(getXmlDate());
    	xml.setCn("myCn");
    	xml.setEmail("my@e.mail");
    	return xml;
    }
    	
	public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestXmlCertificate.instance().saveReferenceXml();
    }
}