package org.exlp.util.xml;

import java.io.FileNotFoundException;

import org.exlp.model.xml.net.Database;
import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestJdomUtil extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJdomUtil.class);
	
	private Document doc;
	private MultiResourceLoader mrl;
	
	@BeforeEach
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
		Assertions.assertTrue(true);
	}
	
	@Test
	public void loadResourceName() throws FileNotFoundException
	{
		String resourceName = "src/test/resources/data/xml/net/Database.xml";
		Database db = JaxbUtil.loadJAXB(mrl.searchIs(resourceName), Database.class);
		String jaxbTxt = JaxbUtil.toString(db);
		Assertions.assertTrue(jaxbTxt.startsWith("<?xml version"));
		
		boolean proceeed = false;
		if(proceeed)
		{
			Document doc = JDomUtil.load(resourceName);
			JDomUtil.debug(doc);
		}
	}
	
	public static void main(String[] args)
	{
		ExlpBootstrap.init();
		
		TestJdomUtil test = new TestJdomUtil();
		test.init();
	}
}
