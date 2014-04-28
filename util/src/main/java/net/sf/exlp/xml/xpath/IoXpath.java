package net.sf.exlp.xml.xpath;

import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoXpath
{
	final static Logger logger = LoggerFactory.getLogger(IoXpath.class);
	
	@SuppressWarnings("unchecked")
	public static synchronized Dir getDir(Dir dir, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(dir);
		
		List<Dir> list = (List<Dir>)context.selectNodes("dir[@code='"+code+"']");
		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+Dir.class.getSimpleName()+" for code="+code);}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Dir.class.getSimpleName()+"s for code="+code);}
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized File getFile(Dir dir, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(dir);
		
		List<File> list = (List<File>)context.selectNodes("file[@code='"+code+"']");
		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+File.class.getSimpleName()+" for code="+code);}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+File.class.getSimpleName()+"s for code="+code);}
		return list.get(0);
	}
	
	public static synchronized File getFileByName(Dir dir, String name) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		for(File f : dir.getFile())
		{
			if(name.equals(f.getName())){return f;}
		}
		throw new ExlpXpathNotFoundException("No "+File.class.getSimpleName()+" for name="+name);
/*		JXPathContext context = JXPathContext.newContext(dir);
		
		List<File> list = (List<File>)context.selectNodes("file[@name='"+name+"']");
		if(list.size()==0){}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+File.class.getSimpleName()+"s for name="+name);}
		return list.get(0);
*/	}
}