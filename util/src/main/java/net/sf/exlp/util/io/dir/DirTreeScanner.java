package net.sf.exlp.util.io.dir;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.factory.xml.io.XmlDirFactory;
import net.sf.exlp.factory.xml.io.XmlFileFactory;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.xml.io.Dir;

public class DirTreeScanner
{
	final static Logger logger = LoggerFactory.getLogger(DirTreeScanner.class);
	
	public static enum Type{dir,file,unknown}
	
	private XmlFileFactory xfFile;
	private XmlDirFactory xfDir;
	
	public DirTreeScanner()
	{
		Dir qDir = new Dir();
		qDir.setName("");
		
		net.sf.exlp.xml.io.File qFile = new net.sf.exlp.xml.io.File();
		qFile.setName("");
		qFile.setLastModifed(DateUtil.toXmlGc(new Date()));

		xfDir = new XmlDirFactory(qDir);
		xfFile = new XmlFileFactory(qDir.getFile().get(0));
		
	}
	public DirTreeScanner(Dir qDir)
	{
		xfDir = new XmlDirFactory(qDir);
		if(Objects.nonNull(qDir.getFile())) {xfFile = new XmlFileFactory(qDir.getFile().get(0));}
	}
	
	private void checkRoot(File f) throws FileNotFoundException
	{
		if(!f.isDirectory()){throw new FileNotFoundException(f.getAbsolutePath()+" is not a directory");}
		if(!f.exists()){throw new FileNotFoundException(f.getAbsolutePath()+" does not exist");}
	}
		
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
	
	public Dir getDirTree(File f, boolean recursive)
	{
		Dir dir = xfDir.build(f);
		for(File subF : f.listFiles())
		{
			if(subF.isDirectory() && recursive)
			{
				dir.getDir().add(getDirTree(subF,recursive));
			}
			else if (subF.isFile() && xfFile!=null)
			{
				dir.getFile().add(xfFile.build(subF));
			}
		}
		
		return dir;
	}
	
	public Dir getDirTree(File f, boolean recursive, FileFilter ff)
	{
		Dir dir = xfDir.build(f);
		for(File subF : f.listFiles(ff))
		{
			if(subF.isDirectory() && recursive)
			{
				dir.getDir().add(getDirTree(subF,recursive));
			}
			else if (subF.isFile() && xfFile!=null)
			{
				dir.getFile().add(xfFile.build(subF));
			}
		}
		
		return dir;
	}
}