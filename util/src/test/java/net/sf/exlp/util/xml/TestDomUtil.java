package net.sf.exlp.util.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.exlp.test.AbstractExlpTst;
import net.sf.exlp.test.ExlpTstBootstrap;

public class TestDomUtil extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestDomUtil.class);
	
	private Document doc;
	
	@Before
	public void init() throws ParserConfigurationException
	{

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		
        DocumentBuilder builder = dbf.newDocumentBuilder();
        doc = builder.newDocument();

        Element element = doc.createElement("root");
//        element.setPrefix("exlp");
//        element.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:exlp", "http://exlp");
      
        
        Element child = doc.createElement("child");
//        child.setPrefix("exlp");
//        child.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:exlp", "http");
        element.appendChild(child);
        
        doc.appendChild(element);

		
	}
	
	@Test
	public void debug()
	{
//		JDomUtil.debug(doc);
		logger.info("Test");
		Assert.assertTrue(true);
		DomUtil.debugDocument(doc);
	}
	
	@Test
	public void removeNs()
	{
		DomUtil.debugDocument(doc);
		DomUtil.renameNamespaceRecursive(doc,null);
		DomUtil.debugDocument(doc);
	}
	
	public static void main(String[] args) throws ParserConfigurationException
	{
		ExlpTstBootstrap.init();
		
		TestDomUtil test = new TestDomUtil();
		test.init();
		test.removeNs();
	}
}
