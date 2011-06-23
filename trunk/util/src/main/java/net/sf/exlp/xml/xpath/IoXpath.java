package net.sf.exlp.xml.xpath;

import java.util.List;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class IoXpath
{
	static Log logger = LogFactory.getLog(IoXpath.class);
	
	private static NsPrefixMapperInterface nsPrefixMapper;
	
	@SuppressWarnings("unchecked")
	public static synchronized Dir getDir(Dir dirs, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Dir dir = null;
		try
		{
			Document doc = JaxbUtil.toDocument(dirs, getNsPrefixMapper());
			XPath xpath = XPath.newInstance( "//io:dir[@code='"+code+"']");
			List<Object> list = xpath.selectNodes(doc);
			if(list.size()==0){throw new ExlpXpathNotFoundException("No dir found for code="+code);}
			else if(list.size()>1){throw new ExlpXpathNotUniqueException("No unique dir for code="+code);}
			Element e = (Element)list.get(0);
			dir = (Dir)JDomUtil.toJaxb(e, Dir.class);
		}
		catch (JDOMException e) {logger.error(e);}
        return dir;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized File getFile(Dir dirs, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		File file = null;
		try
		{
			Document doc = JaxbUtil.toDocument(dirs, getNsPrefixMapper());
			XPath xpath = XPath.newInstance( "//io:file[@code='"+code+"']");
			List<Object> list = xpath.selectNodes(doc);
			if(list.size()==0){throw new ExlpXpathNotFoundException("No file found for code="+code);}
			else if(list.size()>1){throw new ExlpXpathNotUniqueException("No unique file for code="+code);}
			Element e = (Element)list.get(0);
			file = (File)JDomUtil.toJaxb(e, File.class);
		}
		catch (JDOMException e) {logger.error(e);}
        return file;
	}
	
	private static NsPrefixMapperInterface getNsPrefixMapper()
	{
		if(nsPrefixMapper==null){nsPrefixMapper = new ExlpNsPrefixMapper();}
		return nsPrefixMapper; 
	}
}