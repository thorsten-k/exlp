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
import net.sf.exlp.xml.cdata.CdataXmlEscapeHandler;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class JaxbUtil
{
	final static Logger logger = LoggerFactory.getLogger(JaxbUtil.class);
	
	private static NsPrefixMapperInterface nsPrefixMapper;
	
	public static void setNsPrefixMapper(NsPrefixMapperInterface nsPrefixMapper)
	{
		if(JaxbUtil.nsPrefixMapper != null){logger.warn(NsPrefixMapperInterface.class.getSimpleName()+" already set.");}
		JaxbUtil.nsPrefixMapper = nsPrefixMapper;
	}
	public static synchronized <T extends Object> T loadJAXB(ClassLoader classLoader, String xmlFile, Class<T> c) throws FileNotFoundException
	{
		MultiResourceLoader mrl = new MultiResourceLoader(classLoader);
		return loadJAXB(mrl,xmlFile,c);
	}
	public static synchronized <T extends Object> T loadJAXB(File xmlFile, Class<T> c) throws FileNotFoundException
	{
		return loadJAXB(xmlFile.getAbsolutePath(),c);
	}
	public static synchronized <T extends Object> T loadJAXB(String xmlFile, Class<T> c) throws FileNotFoundException
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		return loadJAXB(mrl,xmlFile,c);
	}
	
	private static synchronized <T extends Object> T loadJAXB(MultiResourceLoader mrl, String xmlFile, Class<T> c) throws FileNotFoundException
	{
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
	
	private static synchronized String getCaller()
	{
		int index;
		logger.trace("StackTraceSize"+Thread.currentThread().getStackTrace().length);
		StackTraceElement[] steList = Thread.currentThread().getStackTrace();
		if(steList.length==4){index=3;}
		else{index=4;}
		
		StackTraceElement ste = Thread.currentThread().getStackTrace()[index];
		
		StringBuffer sb = new StringBuffer();
		sb.append("Output invoked by: ");
		sb.append(ste.getClassName());
		sb.append(".").append(ste.getMethodName());
		sb.append("()");
		sb.append("-").append(ste.getLineNumber());
		sb.append(" (").append(ste.getFileName()).append(")");
		return sb.toString();
	}
	
	public static synchronized void trace(Object jaxb)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace(getCaller());
			output(System.out, jaxb, null,true);
		}
	}
	
	public static synchronized void debug(Object jaxb)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug(getCaller());
			output(System.out, jaxb, null,true);
		}
	}
	
	public static synchronized void info(Object jaxb)
	{
		if(logger.isInfoEnabled())
		{
			logger.info(getCaller());
			output(System.out, jaxb, null,true);
		}
	}
	
	public static synchronized void warn(Object jaxb)
	{
		if(logger.isWarnEnabled())
		{
			logger.warn(getCaller());
			output(System.out, jaxb , null,true);
		}
	}
	
	public static synchronized void error(Object jaxb)
	{
		if(logger.isErrorEnabled())
		{
			logger.error(getCaller());
			output(System.out, jaxb, null,true);
		}
	}
	
	public static synchronized void save(File f, Object jaxb, boolean formatted){save(f, jaxb ,null,formatted);}
	public static synchronized void save(File f, Object jaxb, DocType doctype, boolean formatted)
	{
		OutputStream os=null;
		try
		{
			if(f.getAbsolutePath().endsWith(".gz"))
			{
				os = new GZIPOutputStream(new FileOutputStream(f));
			}
			else {os = new FileOutputStream(f);}
			
			output(os, jaxb, doctype, formatted);
			os.close();
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
	
	public static synchronized InputStream toInputStream(Object jaxb, boolean formatted){return toInputStream(jaxb, null, formatted);}
	public static synchronized InputStream toInputStream(Object jaxb, DocType doctype, boolean formatted)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			output(os, jaxb, doctype, formatted);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return is;
		}
		catch (IOException e) {logger.error("",e);}
		return null;
	}
	
	public static synchronized void output(OutputStream os, Object jaxb){output(os, jaxb,null, true);}
	public static synchronized void output(OutputStream os, Object jaxb, boolean formatted){output(os, jaxb,null, formatted);}
	public static synchronized void output(OutputStream os, Object jaxb, DocType doctype, boolean formatted)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty("com.sun.xml.bind.marshaller.CharacterEscapeHandler",new CdataXmlEscapeHandler("UTF-8"));
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
			if(nsPrefixMapper!=null){m.setProperty("com.sun.xml.bind.namespacePrefixMapper",nsPrefixMapper);}
			if(doctype!=null){m.setProperty("com.sun.xml.bind.xmlHeaders", JDomUtil.toString(doctype));}
			m.marshal( jaxb, os);
		}
		catch (JAXBException e) {logger.error("",e);}
	}
	
	@Deprecated
	public static void toOutputStream(Object xml, OutputStream os)
	{
		logger.warn("Deprecated. Use: output(os, xml, nsPrefixMapper)");
		output(os, xml);
	}
	
	@Deprecated public static synchronized void output(Writer w, Object xml, Object nsPrefixMapper){output(w, xml, nsPrefixMapper,null,true);}
	@Deprecated public static synchronized void output(Writer w, Object xml, Object nsPrefixMapper, DocType doctype, boolean formatted){output(w, xml,null,true);}
	
	public static synchronized void output(Writer w, Object xml){output(w, xml,null,true);}
	public static synchronized void output(Writer w, Object xml, DocType doctype, boolean formatted)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(xml.getClass());
			Marshaller m = context.createMarshaller(); 
			m.setProperty("com.sun.xml.bind.marshaller.CharacterEscapeHandler",new CdataXmlEscapeHandler("UTF-8"));
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
	
	
	@Deprecated public static synchronized String toString(Object xml, NsPrefixMapperInterface nsPrefixMapper){return toString(xml,nsPrefixMapper,true);}
	@Deprecated public static synchronized String toString(Object xml, NsPrefixMapperInterface nsPrefixMapper, boolean printPreamble){return toString(xml,true);}
	
	public static synchronized String toString(Object xml){return toString(xml,true);}
	public static synchronized String toString(Object xml, boolean printPreamble)
	{
		Writer sw = new StringWriter();
		JaxbUtil.output(sw, xml);
		
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