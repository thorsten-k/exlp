package org.exlp.util.jk;

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
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.exlp.interfaces.io.NsPrefixMapperInterface;
import org.exlp.interfaces.util.JaxbInterface;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;

public class JaxbUtil implements JaxbInterface
{
	final static Logger logger = LoggerFactory.getLogger(JaxbUtil.class);
	
	private static NsPrefixMapperInterface nsPrefixMapper;
	
	private static final String NS_PREFIX_MAPPER_PROPERTY = "org.glassfish.jaxb.namespacePrefixMapper";
	
	public static JaxbUtil instance() {return new JaxbUtil();}
	private JaxbUtil()
	{
		
	}
	
	public static void setNsPrefixMapper(NsPrefixMapperInterface nsPrefixMapper)
	{
		if(JaxbUtil.nsPrefixMapper != null){logger.warn(NsPrefixMapperInterface.class.getSimpleName()+" already set.");}
		JaxbUtil.nsPrefixMapper = nsPrefixMapper;
	}
	
	private static Object nsPrefixMapperValue(Object mapper)
	{
		if(mapper instanceof org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper) {return mapper;}
		if(mapper instanceof com.sun.xml.bind.marshaller.NamespacePrefixMapper) {return mapper;}
		if(mapper instanceof NsPrefixMapperInterface) {return new NsPrefixMapperAdapter((NsPrefixMapperInterface)mapper);}
		return mapper;
	}
	
	private static void applyNsPrefixMapper(Marshaller m, Object mapper)
	{
		if(mapper == null) {return;}
		try
		{
			m.setProperty(NS_PREFIX_MAPPER_PROPERTY, nsPrefixMapperValue(mapper));
		}
		catch (JAXBException e) {logRejectedNsPrefixMapper(mapper);}
		catch (RuntimeException e) {logRejectedNsPrefixMapper(mapper);}
	}
	
	private static void logRejectedNsPrefixMapper(Object mapper)
	{
		logger.warn("Namespace prefix mapper "+mapper.getClass().getName()+" rejected by the JAXB RI");
	}
	
	
	@Override public <T> T load(Class<T> c, String resourceName) throws FileNotFoundException
	{
		return JaxbUtil.loadJAXB(resourceName, c);
	}
	
	public static <T extends Object> T load(byte[] data, Class<T> c)
	{
		return loadJAXB(new ByteArrayInputStream(data),c);
	}
	@SuppressWarnings("unused")
	private static <T extends Object> T loadJAXB(ClassLoader classLoader, String xmlFile, Class<T> c) throws FileNotFoundException
	{
		MultiResourceLoader mrl = MultiResourceLoader.instance(classLoader);
		return loadJAXB(mrl,xmlFile,c);
	}
	public static <T extends Object> T loadJAXB(File xmlFile, Class<T> c) throws FileNotFoundException
	{
		return loadJAXB(xmlFile.getAbsolutePath(),c);
	}
	public static <T extends Object> T loadJAXB(String xmlFile, Class<T> c) throws FileNotFoundException
	{
		MultiResourceLoader mrl = MultiResourceLoader.instance();
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
	
	private static String getCaller()
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
	
	// Helpder methods for trace/debug/info/warn/error
	public static synchronized void trace(Object jaxb)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace(getCaller());
			output(System.out, jaxb,true);
		}
	}
	public static synchronized void debug(Object jaxb)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug(getCaller());
			output(System.out, jaxb,true);
		}
	}
	public static synchronized void info(Object jaxb)
	{
		if(logger.isInfoEnabled())
		{
			logger.info(getCaller());
			output(System.out,jaxb,true);
		}
	}
	public static synchronized void warn(Object jaxb)
	{
		if(logger.isWarnEnabled())
		{
			logger.warn(getCaller());
			output(System.out, jaxb,true);
		}
	}
	public static synchronized void error(Object jaxb)
	{
		if(logger.isErrorEnabled())
		{
			logger.error(getCaller());
			output(System.out, jaxb,true);
		}
	}
	
	@Override public void save(Path p, Object jaxb)
	{
		JaxbUtil.save(p.toFile(), jaxb, true);
	}
	public static synchronized void save(File f, Object jaxb, boolean formatted)
	{
		OutputStream os=null;
		try
		{
			if(f.getAbsolutePath().endsWith(".gz"))
			{
				os = new GZIPOutputStream(new FileOutputStream(f));
			}
			else {os = new FileOutputStream(f);}
			
			output(os, jaxb, formatted);
			os.close();
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
	
	public static byte[] toBytes(Object jaxb)
	{
		byte[] data;
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			output(os, jaxb, true);
			data = os.toByteArray();
			os.close();
			return data;
		}
		catch (IOException e) {logger.error("",e);}
		return null;
	}
	public static InputStream toInputStream(Object jaxb, boolean formatted)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			output(os, jaxb, formatted);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return is;
		}
		catch (IOException e) {logger.error("",e);}
		return null;
	}
	
	public static synchronized void output(OutputStream os, Object jaxb){output(os, jaxb, true);}
	public static synchronized void output(OutputStream os, Object jaxb, boolean formatted)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
			
			applyNsPrefixMapper(m, nsPrefixMapper);
			m.marshal( jaxb, os);
		}
		catch (JAXBException e) {logger.error("",e);}
	}
		
	public static synchronized void output(Writer w, Object xml){output(w, xml,null,true);}
	private static synchronized void output(Writer w, Object xml, DocType doctype, boolean formatted)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(xml.getClass());
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
			applyNsPrefixMapper(m, nsPrefixMapper);
			if(doctype!=null){m.setProperty("com.sun.xml.bind.xmlHeaders", JDomUtil.toString(doctype));}
			m.marshal(xml, w);
		}
		catch (JAXBException e) {logger.error("",e);}
	}
	
	public static synchronized Document toDocument(Object jaxb)
	{
		Document doc = null;
		try
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			Marshaller m = context.createMarshaller(); 
			applyNsPrefixMapper(m, nsPrefixMapper);
			m.marshal(jaxb, out);
			
			InputStream is = new ByteArrayInputStream(out.toByteArray());
			doc = new SAXBuilder().build(is);
		}
		catch (JAXBException e) {logger.error("",e);}
		catch (JDOMException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
		return doc;
	}
	
	public static synchronized String toString(Object xml)
	{
		Writer sw = new StringWriter();
		JaxbUtil.output(sw, xml);
		
		String s = sw.toString();
		
		boolean printPreamble = true;
		if(!printPreamble)
		{
			int index = s.indexOf("?>");
			s = s.substring(index+2);
		}
		
		return s;
	}
	
	public static <T extends Object> T copy(Object jaxb, Class<T> c)
	{
		byte[] data = JaxbUtil.toBytes(jaxb);
		return loadJAXB(new ByteArrayInputStream(data),c);
	}
}