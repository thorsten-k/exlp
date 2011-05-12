package net.sf.exlp.xml.xpath;

import java.util.List;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.net.Url;
import net.sf.exlp.xml.net.Urls;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class NetXpath
{
	static Log logger = LogFactory.getLog(NetXpath.class);
	
	private static NsPrefixMapperInterface nsPrefixMapper;
	
	public static synchronized Url getUrl(List<Url> listUrl, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Urls urls = new Urls();
		urls.getUrl().addAll(listUrl);
        return getUrl(urls, code);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized Url getUrl(Urls urls, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Url url = null;
		try
		{
			Document doc = JaxbUtil.toDocument(urls, getNsPrefixMapper());
			XPath xpath = XPath.newInstance( "//net:url[@code='"+code+"']");
			xpath.addNamespace("net", "http://exlp.sf.net/net");
			List<Object> list = xpath.selectNodes(doc);
			if(list.size()==0){throw new ExlpXpathNotFoundException("No url found for code="+code);}
			else if(list.size()>1){throw new ExlpXpathNotUniqueException("No unique url for code="+code);}
			Element e = (Element)list.get(0);
			url = (Url)JDomUtil.toJaxb(e, Url.class);
		}
		catch (JDOMException e) {logger.error(e);}
        return url;
	}
	
	private static NsPrefixMapperInterface getNsPrefixMapper()
	{
		if(nsPrefixMapper==null){nsPrefixMapper = new ExlpNsPrefixMapper();}
		return nsPrefixMapper; 
	}
}