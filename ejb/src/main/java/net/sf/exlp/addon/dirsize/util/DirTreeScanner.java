package net.sf.exlp.addon.dirsize.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.xml.io.Dir;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirTreeScanner
{
	static Log logger = LogFactory.getLog(DirTreeScanner.class);
	
	public static enum Type{dir,file,unknown}
	
	private Dir qDir;
	
	public DirTreeScanner()
	{
		net.sf.exlp.xml.io.File qFile = new net.sf.exlp.xml.io.File();
		qFile.setName("");
		qFile.setLastModifed(DateUtil.getXmlGc4D(new Date()));
		
		qDir = new Dir();
		qDir.setName("");
	}
	public DirTreeScanner(Dir qDir)
	{
		this.qDir=qDir;
	}
	
	private void checkRoot(File f) throws FileNotFoundException
	{
		if(!f.isDirectory()){throw new FileNotFoundException(f.getAbsolutePath()+" is not a directory");}
		if(!f.exists()){throw new FileNotFoundException(f.getAbsolutePath()+" does not exist");}
	}
	
	//Exlp-File

	

	
	//Dir
	public Dir getDir(String rootDir) throws FileNotFoundException
	{
		File f = new File(rootDir);
		checkRoot(f);
		return getDirTree(f);
	}
	
	private Dir getDirTree(File f)
	{
		Dir dir = new Dir();
		if(qDir.isSetName()){dir.setName(f.getName());}
		if(qDir.isSetLastModifed()){dir.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		for(File subF : f.listFiles())
		{
			if(subF.isDirectory())
			{
				dir.getDir().add(getDirTree(subF));
			}
			else if (subF.isFile() && qDir.isSetFile())
			{
				dir.getFile().add(getFile(subF));
			}
		}
		
		return dir;
	}
	
	private net.sf.exlp.xml.io.File getFile(File f)
	{
		net.sf.exlp.xml.io.File file = new net.sf.exlp.xml.io.File();
		//TODO logic deactivated
//		if(qDir.getFile().get(0).isSetName()){file.setName(f.getName());}
//		if(qDir.getFile().get(0).isSetLastModifed()){file.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		return file;
	}
}
