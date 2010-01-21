package net.sf.exlp.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XsltUtil
{
	static Log logger = LogFactory.getLog(XsltUtil.class);
	public static boolean useLog4j = true;
	
	public static synchronized void debug(InputStream xml, String xslt){transform(xml,xslt,System.out);}
	public static synchronized void save(InputStream xml, String xslt, File f)
	{
		try
		{
			FileOutputStream os = new FileOutputStream(f);
			transform(xml, xslt, os);
			os.close();
		}
		catch (FileNotFoundException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
	}
	public static synchronized InputStream toInputStream(InputStream xml, String xslt)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			transform(xml, xslt, os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return is;
		}
		catch (IOException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		return null;
	}
	
	public static synchronized void transform(InputStream xml, String xslt, OutputStream os)
	{
		Source xmlSource = new StreamSource(xml);
        Source xsltSource = new StreamSource(xslt);

        TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer trans;
		try
		{
			trans = transFact.newTransformer(xsltSource);
			trans.transform(xmlSource, new StreamResult(os));
		}
		catch (TransformerConfigurationException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
		catch (TransformerException e) {if(useLog4j){logger.debug(e);}else{System.err.println(e.getMessage());}}
	}
}