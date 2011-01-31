package net.sf.exlp.addon.dirsize.data.factory;

import net.sf.exlp.addon.dirsize.data.jaxb.Dir;
import net.sf.exlp.addon.dirsize.data.jaxb.DirFile;
import net.sf.exlp.addon.dirsize.data.jaxb.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirToDirFileFactory
{
	static Log logger = LogFactory.getLog(DirToDirFileFactory.class);
	public static enum Type {file,dir}
	
	public static synchronized DirFile convert(Dir dir)
	{
		DirFile dirfile = convertDir(dir);
		return dirfile;
	}
	
	private static DirFile convertDir(Dir dir)
	{
		DirFile dirFile = new DirFile();
		dirFile.setType(Type.dir.toString());
		if(dir.isSetId()){dirFile.setId(dir.getId());}
		if(dir.isSetName()){dirFile.setName(dir.getName());}
		if(dir.isSetLastModifed()){dirFile.setLastModifed(dir.getLastModifed());}
		
		for(File file : dir.getFile()){dirFile.getDirFile().add(convertFile(file));}
		for(Dir subDir : dir.getDir()){dirFile.getDirFile().add(convertDir(subDir));}
		
		return dirFile;
	}
	
	private static DirFile convertFile(File file)
	{
		DirFile dirFile = new DirFile();
		dirFile.setType(Type.file.toString());
		if(file.isSetId()){dirFile.setId(file.getId());}
		if(file.isSetName()){dirFile.setName(file.getName());}
		if(file.isSetLastModifed()){dirFile.setLastModifed(file.getLastModifed());}
		return dirFile;
	}
}
