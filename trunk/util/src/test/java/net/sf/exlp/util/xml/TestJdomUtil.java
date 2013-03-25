package net.sf.exlp.util.xml;

import java.io.FileNotFoundException;

import net.sf.exlp.test.AbstractExlpTst;
import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.xml.net.Database;

import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJdomUtil extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestJdomUtil.class);
	
	private Document doc;
	private MultiResourceLoader mrl;
	
	@Before
	public void init()
	{
		doc = new Document();
		Element root = new Element("test");
		
		doc.setRootElement(root);
		
		
		
		mrl = new MultiResourceLoader();
	}
	
	@Test
	public void debug()
	{
//		JDomUtil.debug(doc);
		logger.info("Test");
		Assert.assertTrue(true);
	}
	
	@Test
	public void loadResourceName() throws FileNotFoundException
	{
		String resourceName = "src/test/resources/data/xml/net/Database.xml";
		Database db = JaxbUtil.loadJAXB(mrl.searchIs(resourceName), Database.class);
		String jaxbTxt = JaxbUtil.toString(db);
		Assert.assertTrue(jaxbTxt.startsWith("<?xml version"));
		
		Document doc = JDomUtil.load(resourceName);
		JDomUtil.debug(doc);
	}
	
	public static void main(String[] args)
	{
		ExlpTstBootstrap.init();
		
		TestJdomUtil test = new TestJdomUtil();
		test.init();
	}
}
