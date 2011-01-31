package net.sf.exlp.addon.dirsize.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import net.sf.exlp.addon.dirsize.data.jaxb.Dir;
import net.sf.exlp.util.DateUtil;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirTreeScanner
{
	static Log logger = LogFactory.getLog(DirTreeScanner.class);
	
	private Dir qDir;
	
	public DirTreeScanner()
	{
		net.sf.exlp.addon.dirsize.data.jaxb.File qFile = new net.sf.exlp.addon.dirsize.data.jaxb.File();
		qFile.setName("");
		qFile.setLastModifed(DateUtil.getXmlGc4D(new Date()));
		
		qDir = new Dir();
		qDir.setName("");
		
		qDir.getFile().add(qFile);
		
		
	}
	public DirTreeScanner(Dir qDir)
	{
		this.qDir=qDir;
	}
	
	public Dir getDirTree(String rootDir) throws FileNotFoundException
	{
		File f = new File(rootDir);
		if(!f.isDirectory()){throw new FileNotFoundException(rootDir+" is not a directory");}
		if(!f.exists()){throw new FileNotFoundException(rootDir+" does not exist");}
		String ignorePrefix = getIgnorePrefix( f);
		logger.debug("Path: "+f.getAbsolutePath());
		logger.debug("Ignore: "+ignorePrefix);
		return getDirTree(f);
	}
	
	private Dir getDirTree(File f)
	{
		String ignorePrefix = getIgnorePrefix(f);
		Dir dir = new Dir();
		if(qDir.isSetName()){dir.setName(f.getAbsolutePath().substring(ignorePrefix.length(), f.getAbsolutePath().length()));}
		if(qDir.isSetLastModifed()){dir.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		for(File subF : f.listFiles())
		{
			if(subF.isDirectory()){dir.getDir().add(getDirTree(subF));}
			else if (subF.isFile() && qDir.isSetFile()){dir.getFile().add(getFile(subF));}
		}
		
		return dir;
	}
	
	private net.sf.exlp.addon.dirsize.data.jaxb.File getFile(File f)
	{
		net.sf.exlp.addon.dirsize.data.jaxb.File file = new net.sf.exlp.addon.dirsize.data.jaxb.File();
		if(qDir.getFile().get(0).isSetName()){file.setName(f.getName());}
		if(qDir.getFile().get(0).isSetLastModifed()){file.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		return file;
	}
	
	private String getIgnorePrefix(File f)
	{
		int index = FilenameUtils.indexOfLastSeparator(f.getAbsolutePath());
		return f.getAbsolutePath().substring(0, index+1);
	}

}
