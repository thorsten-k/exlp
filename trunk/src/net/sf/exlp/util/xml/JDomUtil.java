package net.sf.exlp.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.exlp.io.StringBufferOutputStream;
import net.sf.exlp.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.exception.JDomUtilException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;

public class JDomUtil
{
	static Log logger = LogFactory.getLog(JDomUtil.class);
	public static boolean useLog4j = true;
	
	public static synchronized Document txtToDoc(String txt)
	{
		Document doc=null;
		try
		{
			Reader sr = new StringReader(txt);  
			doc = new SAXBuilder().build(sr);
		}
		catch (JDOMException e){if(useLog4j){logger.error(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e){if(useLog4j){logger.error(e);}else{System.err.println(e.getMessage());}}
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
		catch (IOException e){if(useLog4j){logger.error(e);}else{System.err.println(e.getMessage());}}
		return sbos.getStringBuffer().toString();
	}
	
	public static synchronized void debugElement(Element element)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output(element, System.out);
		}
		catch (IOException e){if(useLog4j){logger.error(e);}else{System.err.println(e.getMessage());}}
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
	public static synchronized void save(InputStream is, File f, Format format){save(is, f, format,null);}
	public static synchronized void save(InputStream is, File f, Format format,DocType doctype)
	{
		try
		{
			Document doc = new SAXBuilder().build(is);
			if(doctype!=null){doc.setDocType(doctype);}
			save(doc, f, format);
		}
		catch (JDOMException e){if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e){if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
	}
	public static synchronized InputStream toInputStream(Document doc, Format format)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			outputStream(doc, os, format);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return is;
		}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return null;
	}
	public static synchronized InputStream toInputStream(Element rootElement, Format format)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			outputStream(rootElement, os, format);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return is;
		}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return null;
	}
	public static synchronized void save(Document doc, File f, Format format){save(doc,f,format,"UTF-8");}
	public static synchronized void save(Document doc, File f, Format format, String encoding)
	{
		format.setEncoding(encoding);
		try
		{
			OutputStream os = new FileOutputStream(f);
			outputStream(doc, os, format,encoding);
			os.close();
		}
		catch (FileNotFoundException e) {if(useLog4j){logger.error(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e) {if(useLog4j){logger.error(e);}else{System.err.println(e.getMessage());}}
	}
	public static synchronized void debug(Document doc)
	{
		outputStream(doc, System.out, Format.getPrettyFormat());
		System.out.flush();
	}
	private static synchronized void outputStream(Document doc, OutputStream os, Format format){outputStream(doc, os, format, "UTF-8");}
	private static synchronized void outputStream(Document doc, OutputStream os, Format format, String encoding)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(format);
			OutputStreamWriter osw = new OutputStreamWriter(os,encoding);
			xmlOut.output( doc, osw );
			osw.close();
		} 
		catch (IOException e){if(useLog4j){logger.error(e);}else{System.err.println(e.getMessage());}}
	}
	public static synchronized Object toJaxb(Element rootElement, Class<?> c)
	{
		InputStream is = toInputStream(rootElement, Format.getRawFormat());
		Object result = null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(c);
			Unmarshaller u = jc.createUnmarshaller();
			result = u.unmarshal(is);
		}
		catch (JAXBException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return result;
	}
	
	public static synchronized org.w3c.dom.Document toW3CDocument(Document jdomDoc)
	{
		org.w3c.dom.Document w3cDoc = null;
		try
		{
			InputStream is = JDomUtil.toInputStream(jdomDoc, Format.getRawFormat());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			w3cDoc = builder.parse(is);
		}
		catch (ParserConfigurationException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (SAXException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return w3cDoc;
	}
	
	public static synchronized void debug(Element e)
	{
		outputStream(e, System.out, Format.getPrettyFormat());
//		System.out.flush();
//		System.out.close();
	}
	private static synchronized void outputStream(Element e, OutputStream os, Format format)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(format);
			OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
			xmlOut.output( e, osw );
			osw.close();
		} 
		catch (IOException ex){if(useLog4j){logger.error(ex);}else{System.err.println(ex.getMessage());}}
	}
	
	public static synchronized Document load(File f){return load(f, "UTF-8");}
	public static synchronized Document load(File f, String encoding)
	{
		Document doc = null;
		try
		{
			InputStream is = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(is, encoding);
			doc = new SAXBuilder().build(isr);
		}
		catch (JDOMException e)
		{
			String msg = e.getMessage()+" "+f.getAbsolutePath();
			throw new JDomUtilException(msg);
		}
		catch (IOException e)
		{
			String msg = e.getMessage()+" "+f.getAbsolutePath();
			if(useLog4j){logger.error(msg);}else{System.err.println(msg);}
		}
		return doc;
	} 
	
	public static synchronized Document load(String resourceName){return load(resourceName, "UTF-8");}
	public static synchronized Document load(String resourceName, String encoding)
	{
		Document doc = null;
		try
		{
			MultiResourceLoader mrl = new MultiResourceLoader();
			InputStream is = mrl.searchIs(resourceName);
			InputStreamReader isr = new InputStreamReader(is, encoding);
			doc = new SAXBuilder().build(isr);
		}
		catch (JDOMException e)
		{
			String msg = e.getMessage()+" "+resourceName;
			throw new JDomUtilException(msg);
		}
		catch (IOException e)
		{
			String msg = e.getMessage()+" "+resourceName;
			if(useLog4j){logger.error(msg);}else{System.err.println(msg);}
		}
		return doc;
	}
	
	public static Element setNameSpaceRecursive(Element e, Namespace ns)
	{
		e.setNamespace(ns);
		for(Object o : e.getChildren())
		{
			Element eChild = (Element)o;
			eChild=setNameSpaceRecursive(eChild,ns);
		}
		return e;
	}
	
	public static String toString(DocType doctype)
	{
		StringBufferOutputStream sbos = new StringBufferOutputStream();
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat());
			xmlOut.output(doctype, sbos);
		}
		catch (IOException e){if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return sbos.getStringBuffer().toString();
	}
	
	public static synchronized Document correctNsPrefixes(Document doc,NsPrefixMapperInterface nsPM)
	{
		Namespace ns = Namespace.getNamespace(doc.getRootElement().getNamespaceURI());
		doc.getRootElement().setNamespace(ns);
		correct(doc.getRootElement(), ns);
		return doc;
	}
	
	public static Element correct(Element e, Namespace ns)
	{
		if(e.getNamespace().equals(ns.getURI())){e.setNamespace(ns);}
		
		for(Object o : e.getChildren())
		{
			Element eChild = (Element)o;
			eChild=setNameSpaceRecursive(eChild,ns);
		}
		return e;
	}
}