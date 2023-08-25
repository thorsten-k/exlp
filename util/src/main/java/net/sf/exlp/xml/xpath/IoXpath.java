package net.sf.exlp.xml.xpath;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;

public class IoXpath
{
	final static Logger logger = LoggerFactory.getLogger(IoXpath.class);
	
//	@SuppressWarnings("unchecked")
	public static Dir getDir(Dir dir, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
//		JaxbUtil.info(dir);
		List<Dir> list = new ArrayList<>();
		for(Dir d : dir.getDir())
		{
			if(d.getCode().equals(code)) {list.add(d);}
		}

//		JXPathContext context = JXPathContext.newContext(dir);
//		List<Dir> list = (List<Dir>)context.selectNodes("dir[@code='"+code+"']");
		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+Dir.class.getSimpleName()+" for code="+code);}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Dir.class.getSimpleName()+"s for code="+code);}
		return list.get(0);
	}
	
//	public static net.sf.exlp.xml.jk.io.Dir getDir(net.sf.exlp.xml.jk.io.Dir dir, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
//	{
//		List<net.sf.exlp.xml.jk.io.Dir> list = new ArrayList<>();
//		for(net.sf.exlp.xml.jk.io.Dir d : dir.getDir())
//		{
//			if(d.getCode().equals(code)) {list.add(d);}
//		}
//		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+Dir.class.getSimpleName()+" for code="+code);}
//		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Dir.class.getSimpleName()+"s for code="+code);}
//		return list.get(0);
//	}
	
	@SuppressWarnings("unchecked")
	public static synchronized File getFile(Dir dir, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(dir);
		List<File> list = (List<File>)context.selectNodes("file[@code='"+code+"']");
		
		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+File.class.getSimpleName()+" for code="+code);}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+File.class.getSimpleName()+"s for code="+code);}
		return list.get(0);
	}
//	@SuppressWarnings("unchecked")
//	public static synchronized net.sf.exlp.xml.jk.io.File getFile(net.sf.exlp.xml.jk.io.Dir dir, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
//	{
//		JXPathContext context = JXPathContext.newContext(dir);
//		List<net.sf.exlp.xml.jk.io.File> list = (List<net.sf.exlp.xml.jk.io.File>)context.selectNodes("file[@code='"+code+"']");
//		
//		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+File.class.getSimpleName()+" for code="+code);}
//		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+File.class.getSimpleName()+"s for code="+code);}
//		return list.get(0);
//	}
	
	public static synchronized File getFileByName(Dir dir, String name) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		for(File f : dir.getFile())
		{
			if(name.equals(f.getName())){return f;}
		}
		throw new ExlpXpathNotFoundException("No "+File.class.getSimpleName()+" for name="+name);
	}
}