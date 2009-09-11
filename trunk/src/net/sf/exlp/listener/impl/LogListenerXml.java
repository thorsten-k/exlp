package net.sf.exlp.listener.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.exlp.listener.AbstractLogListener;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.parser.LogParser;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class LogListenerXml extends AbstractLogListener implements LogListener
{
	static Logger logger = Logger.getLogger(LogListenerXml.class);
	
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
		try
		{
			XPath x = XPath.newInstance(xPathExpression);
			List<?> l = x.selectNodes(doc);
			if(l!=null && l.size()>0)
			{
				for(Object o : l)
				{
					Element e = (Element)o;
					String content = e.getText();
					StringTokenizer st = new StringTokenizer(content,"\n");
					while(st.hasMoreElements())
					{
						lp.parseLine((String)st.nextElement());
					}
					
				}
			}
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	public void processMulti(String xPathExpression)
	{
		logger.debug("Evaluating xPath:"+xPathExpression);
		try
		{
			XPath x = XPath.newInstance(xPathExpression);
			List<?> l = x.selectNodes(doc);
			if(l!=null && l.size()>0)
			{
				for(Object o : l)
				{
					Element e = (Element)o;
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
		}
		catch (JDOMException e) {logger.error(e);}
	}
}