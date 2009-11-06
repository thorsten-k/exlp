package net.sf.exlp.util.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class XpathUtil
{
	static Logger logger = Logger.getLogger(XpathUtil.class);
	
	@SuppressWarnings("unchecked")
	public static synchronized List<?> processXPath(String xPathExpression, Document doc)
	{
		List<?> refs = new ArrayList();
		try
		{
			XPath xpath = XPath.newInstance(xPathExpression);
			refs = xpath.selectNodes(doc); 
		}
		catch (JDOMException e) {e.printStackTrace();}
		return refs;
	}
	
	public static synchronized Element processSingleXPath(String xPathExpression, Document doc)
	{
		Element element = null;
		try
		{
			XPath xpath = XPath.newInstance(xPathExpression);
			element = (Element)xpath.selectSingleNode(doc); 
		}
		catch (JDOMException e) {e.printStackTrace();}
		return element;
	}

}