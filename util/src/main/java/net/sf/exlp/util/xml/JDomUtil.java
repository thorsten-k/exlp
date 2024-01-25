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
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.exlp.interfaces.io.NsPrefixMapperInterface;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import net.sf.exlp.util.io.StringBufferOutputStream;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.exception.JDomUtilException;

public class JDomUtil
{
	final static Logger logger = LoggerFactory.getLogger(JDomUtil.class);
	
	private XMLOutputter outputter;
	
	private String encoding;
	private boolean omitDeclaration; public JDomUtil omitDeclaration(boolean value) {this.omitDeclaration=value; return this;}
	private boolean omitEscape; public JDomUtil omitEscape(boolean value) {this.omitEscape=value; return this;}
	public JDomUtil expand(boolean value) {outputter.getFormat().setExpandEmptyElements(value); return this;}
	
	public static JDomUtil instance() {return new JDomUtil();}
	private JDomUtil()
	{
		encoding = "UTF-8";
		omitDeclaration = false;
		Format format = Format.getPrettyFormat();
		
		outputter = new XMLOutputter(format);
	}
	
	public void info(Document doc)
	{
		stream(doc, System.out);
		System.out.flush();
	}
	public void info(Element doc)
	{
		stream(doc, System.out);
		System.out.flush();
	}
	
	public void write(Document doc, Path path)
	{        
		try
		{
			OutputStream os = new FileOutputStream(path.toFile());
			this.stream(doc, os);
			os.close();
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
	
	public void stream(Document doc, OutputStream os)
	{
		Format format = Format.getPrettyFormat();
		format.setExpandEmptyElements(true);
		format.setOmitDeclaration(omitDeclaration);
		
		outputter = new XMLOutputter(format);
		
		try
		{
			if(omitEscape)
			{
				StringWriter sw = new StringWriter();
				outputter.output(doc,sw);
				sw.close();
				
				String content = sw.toString();
				content = content.replaceAll("&lt;","<");
				
				OutputStreamWriter osw = new OutputStreamWriter(os,encoding);
				osw.write(content);
				osw.close();
			}
			else
			{
				OutputStreamWriter osw = new OutputStreamWriter(os,encoding);
				outputter.output(doc,osw);
				osw.close();
			}
		} 
		catch (IOException e){logger.error("",e);}
	}
	
	public void stream(Element element, OutputStream os)
	{
		Format format = Format.getPrettyFormat();
		format.setExpandEmptyElements(true);
		format.setOmitDeclaration(omitDeclaration);
		
		outputter = new XMLOutputter(format);
		
		try
		{
			if(omitEscape)
			{
				StringWriter sw = new StringWriter();
				outputter.output(element,sw);
				sw.close();
				
				String content = sw.toString();
				content = content.replaceAll("&lt;","<");
				
				OutputStreamWriter osw = new OutputStreamWriter(os,encoding);
				osw.write(content);
				osw.close();
			}
			else
			{
				OutputStreamWriter osw = new OutputStreamWriter(os,encoding);
				outputter.output(element,osw);
				osw.close();
			}
		} 
		catch (IOException e){logger.error("",e);}
	}
	
	public void stream(List<Element> elements, StringWriter sw)
	{
		Format format = Format.getPrettyFormat();
		format.setExpandEmptyElements(true);
		format.setOmitDeclaration(omitDeclaration);
		
		outputter = new XMLOutputter(format);
		
		try
		{
			if(omitEscape)
			{
				StringWriter swTmp = new StringWriter();
				outputter.output(elements,swTmp);
				sw.close();
				
				String content = sw.toString();
				content = content.replaceAll("&lt;","<");
				sw.write(content);
			}
			else
			{
				outputter.output(elements,sw);
			}
		} 
		catch (IOException e){logger.error("",e);}
	}
	
	public static Document txtToDoc(String txt) throws JDOMException
	{
		Document doc=null;
		try
		{
			Reader sr = new StringReader(txt);  
			doc = new SAXBuilder().build(sr);
		}
		catch (IOException e){logger.error("Cannot create doc",e);}
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
		catch (IOException e){logger.error("Cannot create XMLOutputter",e);}
		return sbos.getStringBuffer().toString();
	}
	
	public static synchronized void debugElement(Element element)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output(element, System.out);
		}
		catch (IOException e){logger.error("Cannot create XMLOutputter",e);}
	}
	
	public static synchronized void dissect(Document doc)
	{
		Element rootE = doc.getRootElement();
		String logMsg="RootName="+rootE.getName();
		logger.debug(logMsg);
		
		for(Object o :rootE.getChildren())
		{
			Element e=(Element)o;
			logger.debug(e.getName());
			logMsg=e.getName();
			logger.debug(logMsg);
			for(Object oContent : e.getContent())
			{
				if(Text.class.isInstance(oContent))
				{
					Text txt = (Text)oContent;
					logger.debug("\t"+oContent.getClass().getSimpleName()+": "+txt.getText());
				}
				else if(Element.class.isInstance(oContent))
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
		catch (JDOMException e){logger.error("Cannot save doc",e);}
		catch (IOException e){logger.error("Cannot save doc",e);}
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
		catch (IOException e) {logger.error("toInputStream",e);}
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
		catch (IOException e) {logger.error("toInputStream",e);}
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
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
	public static synchronized void debug(Document doc)
	{
		outputStream(doc, System.out, Format.getPrettyFormat());
		System.out.flush();
	}
	public static synchronized void outputStream(Document doc, OutputStream os, Format format){outputStream(doc, os, format, "UTF-8");}
	public static synchronized void outputStream(Document doc, OutputStream os, Format format, String encoding)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(format);
			OutputStreamWriter osw = new OutputStreamWriter(os,encoding);
			xmlOut.output( doc, osw );
			osw.close();
		} 
		catch (IOException e){logger.error("",e);}
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized <T extends Object> T toJaxb(Document doc, Class<T> c)
	{
		InputStream is = toInputStream(doc, Format.getRawFormat());
		T result = null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(c);
			Unmarshaller u = jc.createUnmarshaller();
			result = (T)u.unmarshal(is);
		}
		catch (JAXBException e){logger.error("",e);}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized <T extends Object> T toJaxb(Element rootElement, Class<T> c)
	{
		InputStream is = toInputStream(rootElement, Format.getRawFormat());
		T result = null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(c);
			Unmarshaller u = jc.createUnmarshaller();
			result = (T)u.unmarshal(is);
		}
		catch (JAXBException e){logger.error("",e);}
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
		catch (ParserConfigurationException e) {logger.error("",e);}
		catch (SAXException e){logger.error("",e);}
		catch (IOException e){logger.error("",e);}
		return w3cDoc;
	}
	
	public static String toString(Document doc)
	{
		StringWriter sw = new StringWriter();
//		StringBufferOutputStream sbos = new StringBufferOutputStream();
		outputWriter(doc, sw, Format.getPrettyFormat());
		return sw.toString().trim();
	}
	
	public static synchronized void debug(Element e)
	{
		outputStream(e, System.out, Format.getPrettyFormat());
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
		catch (IOException ex){logger.error("",ex);}
	}
	private static synchronized void outputWriter(Document doc, Writer w, Format format)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(format);
			xmlOut.output(doc,w);
			w.flush();
			w.close();
		} 
		catch (IOException ex){logger.error("",ex);}
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
			logger.error(msg,e);
		}
		return doc;
	} 
	
	public static synchronized Document load(String resourceName){return load(resourceName, "UTF-8");}
	public static synchronized Document load(String resourceName, String encoding)
	{
		try
		{
			MultiResourceLoader mrl = MultiResourceLoader.instance();
			InputStream is = mrl.searchIs(resourceName);
			return load(is,encoding);
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
	}
	public static Document load(InputStream is){return load(is,"UTF-8");}
	public static Document load(InputStream is, String encoding)
	{
		Document doc = null;
		try
		{
			InputStreamReader isr = new InputStreamReader(is, encoding);
			SAXBuilder sax = new SAXBuilder();
			sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			doc = sax.build(isr);
		}
		catch (JDOMException e)
		{
			String msg = e.getMessage();
			throw new JDomUtilException(msg);
		}
		catch (IOException e)
		{
			String msg = e.getMessage();
			logger.error(msg,e);
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
		catch (IOException e){logger.error("",e);}
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
		if(e.getNamespace().getURI().equals(ns.getURI())){e.setNamespace(ns);}
		
		for(Object o : e.getChildren())
		{
			Element eChild = (Element)o;
			eChild=setNameSpaceRecursive(eChild,ns);
		}
		return e;
	}
	
	public static synchronized Document unsetNameSpace(Document doc)
	{
		Namespace ns = Namespace.getNamespace("");
		unsetNameSpace(doc.getRootElement(), ns);
		
		List<Namespace> lAddNs = (List<Namespace>)doc.getRootElement().getAdditionalNamespaces();
		while(lAddNs.size()>0)
		{
			doc.getRootElement().removeNamespaceDeclaration(lAddNs.get(0));
			lAddNs = (List<Namespace>)doc.getRootElement().getAdditionalNamespaces();
		}
		
		return doc;
	}
	
	public static synchronized Element unsetNameSpace(Element e, Namespace ns)
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