package net.sf.exlp.util.xml;

import net.sf.exlp.test.ExlpTstBootstrap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJdomUtil
{
	final static Logger logger = LoggerFactory.getLogger(TestJdomUtil.class);
	
	private Document doc;
	
	@Before
	public void debug()
	{
		doc = new Document();
		Element root = new Element("test");
		
		doc.setRootElement(root);
		
		JDomUtil.debug(doc);
		logger.info("Test");
	}
	
	@Test
	public void test()
	{
		Assert.assertTrue(true);
	}
	
	public static void main(String[] args)
	{
		ExlpTstBootstrap.init();
		
		TestJdomUtil test = new TestJdomUtil();
		test.debug();
	}
}
