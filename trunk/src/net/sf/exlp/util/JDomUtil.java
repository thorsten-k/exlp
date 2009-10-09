package net.sf.exlp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;

import net.sf.exlp.io.StringBufferOutputStream;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class JDomUtil
{
	private static Logger logger = Logger.getLogger(JDomUtil.class);
	public static boolean useLog4j = true;
	
	public static synchronized Document txtToDoc(String txt)
	{
		Document doc=null;
		try
		{
			Reader sr = new StringReader(txt);  
			doc = new SAXBuilder().build(sr);
		}
		catch (JDOMException e){if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e){if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return doc;
	}
	
	public static synchronized String docToTxt(Document doc)
	{
		
		StringBufferOutputStream sbos = new StringBufferOutputStream();
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat());
			xmlOut.output(doc, sbos);
		}
		catch (IOException e){if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return sbos.getStringBuffer().toString();
	}
	
	public static synchronized void debugElement(Element element)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output(element, System.out);
		}
		catch (IOException e){if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
	}
	
	public static synchronized void debugDocument(Document doc)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output(doc, System.out);
		}
		catch (IOException e) {logger.error(e);}
	}
	
	public static synchronized void dissect(Document doc)
	{
		Element rootE = doc.getRootElement();
		String logMsg="RootName="+rootE.getName();
		if(useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
		
		for(Object o :rootE.getChildren())
		{
			Element e=(Element)o;
			logger.debug(e.getName());
			logMsg=e.getName();
			if(useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
			for(Object oContent : e.getContent())
			{
				if(org.jdom.Text.class.isInstance(oContent))
				{
					Text txt = (Text)oContent;
					logger.debug("\t"+oContent.getClass().getSimpleName()+": "+txt.getText());
				}
				else if(org.jdom.Element.class.isInstance(oContent))
				{
					Element child = (Element)oContent;
					logger.debug("\t"+oContent.getClass().getSimpleName()+": "+child.getName());
				}
				else {logger.warn("Unknown content: "+o.getClass().getName());}
			}
		}
	}
	
	public static synchronized void save(Document doc, File f, Format format)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(format);
			
			OutputStream os = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
			
			xmlOut.output( doc, osw );
			osw.close();os.close();
		} 
		catch (IOException e){if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
	}
	
	public static Element unsetNameSpace(Element e, Namespace ns)
	{
		e.setNamespace(ns);
		for(Object o : e.getChildren())
		{
			Element eChild = (Element)o;
			eChild=unsetNameSpace(eChild,ns);
		}
		return e;
	}
}