package net.sf.exlp.util.xml;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class DomUtil
{
	final static Logger logger = LoggerFactory.getLogger(DomUtil.class);
	
	public static synchronized void debugDocument(Document doc)
	{
		try
		{
			TransformerFactory tf = TransformerFactory.newInstance(); 
			Transformer t = tf.newTransformer();
			//Setup indenting to "pretty print"
			t.setOutputProperty(OutputKeys.INDENT,"yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
			t.transform(new DOMSource(doc), new StreamResult(System.out));
		}
		catch (TransformerConfigurationException e) {logger.error("",e);} 
		catch (TransformerException e) {logger.error("",e);} 
	}
	
}