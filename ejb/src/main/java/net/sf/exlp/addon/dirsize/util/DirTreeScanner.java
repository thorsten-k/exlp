package net.sf.exlp.addon.dirsize.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import net.sf.exlp.addon.dirsize.data.ejb.ExlpFile;
import net.sf.exlp.addon.dirsize.data.jaxb.Dir;
import net.sf.exlp.addon.dirsize.data.jaxb.DirFile;
import net.sf.exlp.util.DateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirTreeScanner
{
	static Log logger = LogFactory.getLog(DirTreeScanner.class);
	
	public static enum Type{dir,file,unknown}
	
	private Dir qDir;
	private DirFile qDirFile;
	
	public DirTreeScanner()
	{
		net.sf.exlp.addon.dirsize.data.jaxb.File qFile = new net.sf.exlp.addon.dirsize.data.jaxb.File();
		qFile.setName("");
		qFile.setLastModifed(DateUtil.getXmlGc4D(new Date()));
		
		qDir = new Dir();
		qDir.setName("");
		
		qDir.getFile().add(qFile);
		
		qDirFile = new DirFile();
		qDirFile.setName("");
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
	
	public ExlpFile getExlpFile(String rootDir) throws FileNotFoundException
	{
		File f = new File(rootDir);
		checkRoot(f);
		return getExlpFile(f,null);
	}
	
	private ExlpFile getExlpFile(File f, ExlpFile root)
	{
		ExlpFile ef = new ExlpFile();
		ef.setParent(root);
		ef.setName(f.getName());
		ef.setModified(new Date(f.lastModified()));
		
		if(f.isFile()){ef.setType(ExlpFile.Type.file);}
		else if(f.isDirectory())
		{
			ef.setType(ExlpFile.Type.dir);
			for(File subF : f.listFiles())
			{
				ef.getChilds().add(getExlpFile(subF,ef));
			}
		}
		else {ef.setType(ExlpFile.Type.unknown);}
	
		return ef;
	}
	
	//DirFile
	
	public DirFile getDirFile(String rootDir) throws FileNotFoundException
	{
		File f = new File(rootDir);
		checkRoot(f);
		return getDirFile(f);
	}
	
	private DirFile getDirFile(File f)
	{
		DirFile df = new DirFile();
		if(qDirFile.isSetName()){df.setName(f.getName());}
		if(qDirFile.isSetLastModifed()){df.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		
		if(f.isFile()){df.setType(Type.file.toString());}
		else if(f.isDirectory())
		{
			df.setType(Type.dir.toString());
			for(File subF : f.listFiles())
			{
				df.getDirFile().add(getDirFile(subF));
			}
		}
		else {df.setType(Type.unknown.toString());}
	
		return df;
	}
	
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
}
