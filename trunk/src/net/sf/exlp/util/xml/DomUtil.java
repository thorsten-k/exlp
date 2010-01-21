package net.sf.exlp.util.xml;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

public class DomUtil
{
	static Log logger = LogFactory.getLog(DomUtil.class);
	
	public static synchronized void debugDocument(Document doc)
	{
		try
		{
			TransformerFactory tf = TransformerFactory.newInstance(); 
			Transformer t = tf.newTransformer();
			t.transform(new DOMSource(doc), new StreamResult(System.out));
		}
		catch (TransformerConfigurationException e) {logger.error(e);} 
		catch (TransformerException e) {logger.error(e);} 
	}
	
}