package net.sf.exlp.util.xml;

import net.sf.exlp.test.ExlpTstBootstrap;

import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJdomUtil
{
	final static Logger logger = LoggerFactory.getLogger(TestJdomUtil.class);
	
	public void debug()
	{
		Document doc = new Document();
		Element root = new Element("test");
		
		doc.setRootElement(root);
		
		JDomUtil.debug(doc);
		logger.info("Test");
	}
	
	
	public static void main(String[] args)
	{
		ExlpTstBootstrap.init();
		
		TestJdomUtil test = new TestJdomUtil();
		test.debug();
	}
}
