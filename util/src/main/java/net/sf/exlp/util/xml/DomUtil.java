package net.sf.exlp.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	
	public static void debugDocument(Element element)
	{
		try
		{
			TransformerFactory tf = TransformerFactory.newInstance(); 
			Transformer t = tf.newTransformer();
			//Setup indenting to "pretty print"
			t.setOutputProperty(OutputKeys.INDENT,"yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
			t.transform(new DOMSource(element), new StreamResult(System.out));
		}
		catch (TransformerConfigurationException e) {logger.error("",e);} 
		catch (TransformerException e) {logger.error("",e);} 
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized <T extends Object> T toJaxb(Element element, Class<T> clJaxb)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			TransformerFactory tf = TransformerFactory.newInstance(); 
			Transformer t = tf.newTransformer();
			
			t.setOutputProperty(OutputKeys.INDENT,"yes");
			t.transform(new DOMSource(element), new StreamResult(os));
			
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			
			T result = null;
			try
			{
				JAXBContext jc = JAXBContext.newInstance(clJaxb);
				Unmarshaller u = jc.createUnmarshaller();
				result = (T)u.unmarshal(is);
			}
			catch (JAXBException e){logger.error("",e);}
			return result;
			
		}
		catch (TransformerConfigurationException e){e.printStackTrace();}
		catch (TransformerException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static void renameNamespaceRecursive(Node node, String namespace)
	{
        Document document = node.getOwnerDocument();
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            document.renameNode(node, namespace, node.getNodeName());
        }
        NodeList list = node.getChildNodes();
        for (int i=0; i<list.getLength(); ++i)
        {
            renameNamespaceRecursive(list.item(i), namespace);
        }
    }
	
	public static Document cleanNameSpace(Document doc)
	{

	    NodeList list = doc.getChildNodes();
	    for (int i = 0; i < list.getLength(); i++)
	    {
	        removeNamSpace(list.item(i), "");
	    }

	    return doc;
	}
	private static void removeNamSpace(Node node, String nameSpaceURI) {

	    if (node.getNodeType() == Node.ELEMENT_NODE)
	    {
	        Document ownerDoc = node.getOwnerDocument();
	        NamedNodeMap map = node.getAttributes();
	        Node n;
	        while (!(0==map.getLength()))
	        {
	            n = map.item(0);
	            map.removeNamedItemNS(n.getNamespaceURI(), n.getLocalName());
	        }
	        ownerDoc.renameNode(node, nameSpaceURI, node.getLocalName());
	    }
	    NodeList list = node.getChildNodes();
	    for (int i = 0; i < list.getLength(); i++)
	    {
	        removeNamSpace(list.item(i), nameSpaceURI);
	    }
	}
}