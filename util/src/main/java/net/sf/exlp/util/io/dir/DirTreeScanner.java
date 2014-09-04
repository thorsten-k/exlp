package net.sf.exlp.util.io.dir;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.xml.io.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirTreeScanner
{
	final static Logger logger = LoggerFactory.getLogger(DirTreeScanner.class);
	
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
		return getDir(rootDir,true);
	}
	
	public Dir getDir(String rootDir,boolean recursive) throws FileNotFoundException
	{
		File f = new File(rootDir);
		checkRoot(f);
		return getDirTree(f,recursive);
	}
	
	public Dir getDirTree(File f,boolean recursive)
	{
		Dir dir = new Dir();
		if(qDir.isSetName()){dir.setName(f.getName());}
		if(qDir.isSetLastModifed()){dir.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		for(File subF : f.listFiles())
		{
			if(subF.isDirectory() && recursive)
			{
				dir.getDir().add(getDirTree(subF,recursive));
			}
			else if (subF.isFile() && qDir.isSetFile())
			{
				dir.getFile().add(getFile(subF,qDir.getFile().get(0)));
			}
		}
		
		return dir;
	}
	
	private net.sf.exlp.xml.io.File getFile(File f,net.sf.exlp.xml.io.File qFile)
	{
		net.sf.exlp.xml.io.File file = new net.sf.exlp.xml.io.File();
		if(qFile.isSetName()){file.setName(f.getName());}
		if(qFile.isSetSize()){file.setSize(f.length());}
		if(qFile.isSetLastModifed()){file.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		return file;
	}
}
