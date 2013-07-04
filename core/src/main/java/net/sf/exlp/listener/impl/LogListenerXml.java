package net.sf.exlp.listener.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.listener.AbstractLogListener;
import net.sf.exlp.listener.LogListener;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogListenerXml extends AbstractLogListener implements LogListener
{
	final static Logger logger = LoggerFactory.getLogger(LogListenerXml.class);
	
	BufferedReader myBufferedReader;
	Document doc;
	
	public LogListenerXml(String fileName,LogParser lp)
	{
		super(lp);
		File f = new File(fileName);
		if(!f.exists()){exit("File "+f.getAbsolutePath()+" does not exist!");}
		else{logger.debug("reading "+f.getAbsolutePath());}
		try
		{
			doc = new SAXBuilder().build(f);
			doc.setRootElement(unsetNameSpace(doc.getRootElement()));
		}
		catch (JDOMException e) {exit("JDomException: "+e.getMessage());}
		catch (IOException e) {exit("IOException: "+e.getMessage());}
	}
	
	private Element unsetNameSpace(Element e)
	{
		e.setNamespace(null);
		for(Object o : e.getChildren())
		{
			Element eChild = (Element)o;
			eChild=unsetNameSpace(eChild);
		}
		return e;
	}
	
	public void processSingle(String xPathExpression)
	{
		logger.debug("Evaluating xPath:"+xPathExpression);

		XPathExpression<Element> xpath = XPathFactory.instance().compile(xPathExpression, Filters.element());
		List<Element> elements = xpath.evaluate(doc);
		for(Element e : elements)
		{
			String content = e.getText();
			StringTokenizer st = new StringTokenizer(content,"\n");
			while(st.hasMoreElements())
			{
				lp.parseLine((String)st.nextElement());
			}
			
		}
	}
	
	public void processMulti(String xPathExpression)
	{
		logger.debug("Evaluating xPath:"+xPathExpression);
		
		XPathExpression<Element> xpath = XPathFactory.instance().compile(xPathExpression, Filters.element());
		List<Element> elements = xpath.evaluate(doc);
		
		for(Element e : elements)
		{
			String content = e.getText();
			StringTokenizer st = new StringTokenizer(content,"\n");
			ArrayList<String> lines = new ArrayList<String>();
			while(st.hasMoreElements())
			{
				lines.add((String)st.nextElement());
			}
			lp.parseItem(lines);
		}
	}
	
	@Override
	public void close() {}
}