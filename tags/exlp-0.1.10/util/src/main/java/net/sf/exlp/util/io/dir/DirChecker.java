package net.sf.exlp.util.io.dir;

import java.io.File;

import net.sf.exlp.exception.ExlpConfigurationException;

public class DirChecker
{
	public static boolean isFileAnDirectory(File f)
	{
		try
		{
			checkFileIsDirectory(f);
			return true;
		}
		catch (ExlpConfigurationException e) {}
		return false;
	}
	
	public static void checkFileIsDirectory(File f) throws ExlpConfigurationException
	{
		if(!f.exists()){throw new ExlpConfigurationException("Directory "+f.getAbsolutePath()+" does not exist");}
		if(!f.isDirectory()){throw new ExlpConfigurationException(f.getAbsolutePath()+" is not a directory");}
	}
	
	
	public static boolean isParentAnDir(File f)
	{
		try
		{
			checkParentIsAnDir(f);
			return true;
		}
		catch (ExlpConfigurationException e) {}
		return false;
	}
	public static void checkParentIsAnDir(File f) throws ExlpConfigurationException
	{
		File fPackage = f.getParentFile();
		
		if(!fPackage.exists()){throw new ExlpConfigurationException("Directory "+fPackage.getAbsolutePath()+" does not exist");}
		if(!fPackage.isDirectory()){throw new ExlpConfigurationException(fPackage.getAbsolutePath()+" is not a directory");}
	}
}
