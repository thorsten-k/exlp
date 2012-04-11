package net.sf.exlp.util.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxbUtil6
{
	final static Logger logger = LoggerFactory.getLogger(JaxbUtil6.class);
	
	public static synchronized void debug(Class<?> c, Object jaxb){debug(c, jaxb, null);}
	public static synchronized void debug(Class<?> c, Object jaxb, Object nsPrefixMapper)
	{
		logger.debug("JAXB Debug from class "+c.getSimpleName());
		output(System.out, jaxb, nsPrefixMapper);
	}
	
	public static synchronized void save(File f, Object jaxb){save(f, jaxb, null);}
	public static synchronized void save(File f, Object jaxb, Object nsPrefixMapper)
	{
		OutputStream os=null;
		try
		{
			if(f.getAbsolutePath().endsWith(".gz"))
			{
				os = new GZIPOutputStream(new FileOutputStream(f));
			}
			else {os = new FileOutputStream(f);}
			
			output(os, jaxb, nsPrefixMapper);
			os.close();
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
	
	public static synchronized void output(OutputStream os, Object jaxb){output(os, jaxb,null);}
	public static synchronized void output(OutputStream os, Object jaxb, Object nsPrefixMapper)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			 
			XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
			xmlStreamWriter.setPrefix("ofx", "http://www.openfuxml.org");
			
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(jaxb, xmlStreamWriter);
		}
		catch (JAXBException e) {logger.error("",e);}
		catch (XMLStreamException e) {logger.error("",e);}
		catch (FactoryConfigurationError e) {logger.error("",e);}
	}
}