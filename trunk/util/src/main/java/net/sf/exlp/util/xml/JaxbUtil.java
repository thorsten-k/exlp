package net.sf.exlp.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class JaxbUtil
{
	final static Logger logger = LoggerFactory.getLogger(JaxbUtil.class);
	
	public static synchronized <T extends Object> T loadJAXB(String xmlFile, Class<T> c) throws FileNotFoundException
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		T result = null;

		InputStream is=null;
		InputStream resourceIs = mrl.searchIs(xmlFile);
		
		if(xmlFile.endsWith(".gz"))
		{
			try
			{
				GZIPInputStream gzIs = new GZIPInputStream(resourceIs);
				is=gzIs;
			}
			catch (IOException e) {logger.error("",e);}
		}
		else
		{
			is = resourceIs;
		}
		
		result = loadJAXB(is,c);

		return result;
	}
	@SuppressWarnings("unchecked")
	public static synchronized <T extends Object> T loadJAXB(InputStream is, Class<T> c)
	{
		T result = null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(c);
			Unmarshaller u = jc.createUnmarshaller();
			result = (T)u.unmarshal(is);
		}
		catch (JAXBException e) {logger.error("",e);}
		return result;
	}
	
	@Deprecated public static synchronized void debug(Object jaxb){debug(jaxb, null);}
	
	@Deprecated public static synchronized void debug(Object jaxb, Object nsPrefixMapper){debug(jaxb, nsPrefixMapper,null);}
	
	@Deprecated public static synchronized void debug(Object jaxb, Object nsPrefixMapper, DocType doctype)
	{
		logger.warn("This method is deprecated. Use: JaxbUtil.debug(this.getClass(),jaxb);");
		output(System.out, jaxb, nsPrefixMapper, doctype,true);
	}
	
	public static synchronized void debug(Class<?> c, Object jaxb){debug2(c, jaxb, null);}
	public static synchronized void debug2(Class<?> c, Object jaxb, Object nsPrefixMapper){debug(c, jaxb, nsPrefixMapper,null);}
	public static synchronized void debug(Class<?> c, Object jaxb, Object nsPrefixMapper, DocType doctype)
	{
		logger.debug("JAXB Debug from class "+c.getSimpleName());
		output(System.out, jaxb, nsPrefixMapper, doctype,true);
	}
	
	public static synchronized void save(File f, Object jaxb, boolean formatted){save(f, jaxb, null, null,formatted);}
	public static synchronized void save(File f, Object jaxb, Object nsPrefixMapper,boolean formatted){save(f, jaxb, nsPrefixMapper,null,formatted);}
	public static synchronized void save(File f, Object jaxb, Object nsPrefixMapper, DocType doctype, boolean formatted)
	{
		OutputStream os=null;
		try
		{
			if(f.getAbsolutePath().endsWith(".gz"))
			{
				os = new GZIPOutputStream(new FileOutputStream(f));
			}
			else {os = new FileOutputStream(f);}
			
			output(os, jaxb, nsPrefixMapper, doctype, formatted);
			os.close();
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
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
		catch (IOException e) {logger.error("",e);}
		return null;
	}
	
	public static synchronized void output(OutputStream os, Object jaxb, boolean formatted){output(os, jaxb,null,null, formatted);}
	public static synchronized void output(OutputStream os, Object jaxb, Object nsPrefixMapper){output(os, jaxb,nsPrefixMapper,null, true);}
	public static synchronized void output(OutputStream os, Object jaxb, Object nsPrefixMapper, boolean formatted){output(os, jaxb,nsPrefixMapper,null, formatted);}
	public static synchronized void output(OutputStream os, Object jaxb, Object nsPrefixMapper, DocType doctype, boolean formatted)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
			if(nsPrefixMapper!=null){m.setProperty("com.sun.xml.bind.namespacePrefixMapper",nsPrefixMapper);}
			if(doctype!=null){m.setProperty("com.sun.xml.bind.xmlHeaders", JDomUtil.toString(doctype));}
			m.marshal( jaxb, os);
		}
		catch (JAXBException e) {logger.error("",e);}
	}
	
	@Deprecated
	public static void toOutputStream(Object xml, OutputStream os, NsPrefixMapperInterface nsPrefixMapper)
	{
		logger.warn("Deprecated. Use: output(os, xml, nsPrefixMapper)");
		output(os, xml, nsPrefixMapper);
	}
	
	public static synchronized void output(Writer w, Object xml, Object nsPrefixMapper){output(w, xml, nsPrefixMapper,null,true);}
	public static synchronized void output(Writer w, Object xml, Object nsPrefixMapper, DocType doctype, boolean formatted)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(xml.getClass());
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
			if(nsPrefixMapper!=null){m.setProperty("com.sun.xml.bind.namespacePrefixMapper",nsPrefixMapper);}
			if(doctype!=null){m.setProperty("com.sun.xml.bind.xmlHeaders", JDomUtil.toString(doctype));}
			m.marshal(xml, w);
		}
		catch (JAXBException e) {logger.error("",e);}
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
		catch (JAXBException e) {logger.error("",e);}
		catch (JDOMException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
		return doc;
	}
	
	public static synchronized String toString(Object jaxb){return toString(jaxb,null);}
	public static synchronized String toString(Object jaxb, NsPrefixMapperInterface nsPrefixMapper){return toString(jaxb,nsPrefixMapper,true);}
	public static synchronized String toString(Object xml, NsPrefixMapperInterface nsPrefixMapper, boolean printPreamble)
	{
		Writer sw = new StringWriter();
		
		JaxbUtil.output(sw, xml, nsPrefixMapper);
		
		
		String s = sw.toString();
		if(!printPreamble)
		{
			int index = s.indexOf("?>");
			s = s.substring(index+2);
		}
		
		return s;
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
		catch (JAXBException e) {logger.error("",e);}
		catch (ParserConfigurationException e) {logger.error("",e);}
		catch (SAXException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
		return doc;
	}
}