package net.sf.exlp.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;

public class JaxbUtil
{
	static Log logger = LogFactory.getLog(JaxbUtil.class);
	public static boolean useLog4j = true;
	
	public static synchronized Object loadJAXB(String xmlFile, Class<?> c)
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		Object result = null;
		try
		{
			result = loadJAXB(mrl.searchIs(xmlFile),c);
		}
		catch (FileNotFoundException e) {if(useLog4j){logger.error(e);}else{System.err.println(e.getMessage());}}
		return result;
	}
	public static synchronized Object loadJAXB(InputStream is, Class<?> c)
	{
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
	
	public static synchronized void debug(Object jaxb){debug(jaxb, null);}
	public static synchronized void debug(Object jaxb, Object nsPrefixMapper){debug(jaxb, nsPrefixMapper,null);}
	public static synchronized void debug(Object jaxb, Object nsPrefixMapper, DocType doctype)
	{
		output(System.out, jaxb, nsPrefixMapper, doctype,true);
	}
	public static synchronized void save(File f, Object jaxb, boolean formatted){save(f, jaxb, null, null,formatted);}
	public static synchronized void save(File f, Object jaxb, Object nsPrefixMapper,boolean formatted){save(f, jaxb, nsPrefixMapper,null,formatted);}
	public static synchronized void save(File f, Object jaxb, Object nsPrefixMapper, DocType doctype, boolean formatted)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(f);
			output(fos, jaxb, nsPrefixMapper, doctype, formatted);
			fos.close();
		}
		catch (FileNotFoundException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}	
	}
	
	public static synchronized InputStream toInputStream(Object jaxb, Object nsPrefixMapper,boolean formatted){return toInputStream(jaxb, nsPrefixMapper, null, formatted);}
	public static synchronized InputStream toInputStream(Object jaxb, Object nsPrefixMapper, DocType doctype, boolean formatted)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			output(os, jaxb, nsPrefixMapper, doctype, formatted);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return is;
		}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return null;
	}
	
	public static synchronized void output(OutputStream os, Object jaxb, boolean formatted){output(os, jaxb,null,null, formatted);}
	public static synchronized void output(OutputStream os, Object jaxb, Object nsPrefixMapper, DocType doctype, boolean formatted)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
			if(nsPrefixMapper!=null){m.setProperty("com.sun.xml.bind.namespacePrefixMapper",nsPrefixMapper);}
			if(doctype!=null){m.setProperty("com.sun.xml.bind.xmlHeaders", JDomUtil.toString(doctype));}
            
			m.marshal( jaxb, os);
		}
		catch (JAXBException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
	}
	
	public static synchronized Document toDocument(Object jaxb){return toDocument(jaxb,null);}
	public static synchronized Document toDocument(Object jaxb, Object nsPrefixMapper)
	{
		Document doc = null;
		try
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			Marshaller m = context.createMarshaller(); 
			if(nsPrefixMapper!=null)
			{
				m.setProperty("com.sun.xml.bind.namespacePrefixMapper",nsPrefixMapper);
			}
			m.marshal(jaxb, out);
			
			InputStream is = new ByteArrayInputStream(out.toByteArray());
			doc = new SAXBuilder().build(is);
		}
		catch (JAXBException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (JDOMException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return doc;
	}
	
	public static synchronized org.w3c.dom.Document toW3CDocument(Object jaxb){return toW3CDocument(jaxb,null);}
	public static synchronized org.w3c.dom.Document toW3CDocument(Object jaxb, Object nsPrefixMapper)
	{
		org.w3c.dom.Document doc = null;
		try
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			Marshaller m = context.createMarshaller(); 
			if(nsPrefixMapper!=null)
			{
				m.setProperty("com.sun.xml.bind.namespacePrefixMapper",nsPrefixMapper);
			}
			m.marshal(jaxb, out);
			
			InputStream is = new ByteArrayInputStream(out.toByteArray());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
		}
		catch (JAXBException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (ParserConfigurationException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (SAXException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return doc;
	}
}