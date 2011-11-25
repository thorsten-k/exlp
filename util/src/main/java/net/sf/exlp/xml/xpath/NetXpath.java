package net.sf.exlp.xml.xpath;

import java.util.List;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.xml.net.Url;
import net.sf.exlp.xml.net.Urls;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetXpath
{
	final static Logger logger = LoggerFactory.getLogger(NetXpath.class);
	
	public static synchronized Url getUrl(List<Url> listUrl, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Urls urls = new Urls();
		urls.getUrl().addAll(listUrl);
        return getUrl(urls, code);
	}
	
	public static synchronized Url getUrl(Urls urls, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(urls);
		
		@SuppressWarnings("unchecked")
		List<Url> list = (List<Url>)context.selectNodes("url[@code='"+code+"']");
		if(list.size()==0){throw new ExlpXpathNotFoundException("No URL for code="+code);}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple URLs for code="+code);}
		return list.get(0);
	}
}