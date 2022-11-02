package net.sf.exlp.util.io;

import java.io.FileNotFoundException;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;
import net.sf.exlp.xml.xpath.IoXpath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpCentralConfigPointer
{
	final static Logger logger = LoggerFactory.getLogger(ExlpCentralConfigPointer.class);
	
	public static java.io.File getFile(String codeApp) throws ExlpConfigurationException
	{
		return getFile(codeApp, "root");
	}
	
	public static java.io.File getFile(String codeApp, String codeConf) throws ExlpConfigurationException
	{
		java.io.File fHome = new java.io.File(System.getProperty("user.home"));
		java.io.File fMvn = new java.io.File(fHome,".m2");
		if(!fHome.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fHome.getAbsolutePath());}
		if(!fMvn.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fMvn.getAbsolutePath());}
		return ExlpCentralConfigPointer.getFile(fMvn, "exlp.xml", codeApp, codeConf);
	}
	
	public static java.io.File resolveJsonFile(String codeApp, String codeConf) throws ExlpConfigurationException
	{
		java.io.File fHome = new java.io.File(System.getProperty("user.home"));
		java.io.File fMvn = new java.io.File(fHome,".m2");
		if(!fHome.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fHome.getAbsolutePath());}
		if(!fMvn.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fMvn.getAbsolutePath());}
//		return ExlpCentralConfigPointer.getFile(fMvn, "exlp.xml", codeApp, codeConf);
		throw new ExlpConfigurationException("Migration for native");
	}
	
	private static java.io.File getFile(java.io.File fDir, String fileName, String codeApp, String codeConf) throws ExlpConfigurationException
	{
		Dir dir;
		Dir dirApp;
		
		java.io.File f = new java.io.File(fDir,fileName);
		if(!f.exists())
		{
			logger.warn("ExLP Central Config Pointer does not exist ... creating dummy");
			create(f, codeApp, codeConf);
			String errorMsg = "ExLP Central Config Pointer created. You have to edit this file, otherwise you will get a permanent error!";
			logger.warn(errorMsg);
			throw new ExlpConfigurationException(errorMsg);
		}
		
		try
		{
			dir = JaxbUtil.loadJAXB(f.getAbsolutePath(), Dir.class);
//			System.out.println(Dir.class.getSimpleName()+" with "+dir.getDir().size()+" elements");
		}
		catch (FileNotFoundException e) {throw new ExlpConfigurationException(e.getMessage());}
		
		try{dirApp = IoXpath.getDir(dir, codeApp);}
		catch (ExlpXpathNotFoundException e)
		{
			logger.warn("<dir> does not exist, creating dummy");
			appendDir(f, dir, codeApp, codeConf);
			String errorMsg = "Dummy <dir> created. You have to edit the file ("+f.getAbsolutePath()+"), otherwise you will get a permanent error!";
			logger.warn(errorMsg);
			throw new ExlpConfigurationException(errorMsg);
		}
		catch (ExlpXpathNotUniqueException e) {throw new ExlpConfigurationException(e.getMessage());}
		
		try
		{
			File fXml = IoXpath.getFile(dirApp, codeConf);
			java.io.File fConf = new java.io.File(fXml.getName());
			if(!fConf.exists()){throw new ExlpConfigurationException("File ("+fConf.getAbsolutePath()+") does not exist for app="+codeApp+" code="+codeConf+" in : "+f.getAbsolutePath());}
			logger.debug("Using config: "+fConf.getAbsolutePath());
			return fConf;
		}
		catch (ExlpXpathNotFoundException e) {throw new ExlpConfigurationException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new ExlpConfigurationException(e.getMessage());}
	}
	
	private static void create(java.io.File f, String codeApp, String codeConf)
	{		
		Dir dir = new Dir();
		appendDir(f, dir, codeApp, codeConf);
	}
	
	private static void appendDir(java.io.File f, Dir dir, String codeApp, String codeConf)
	{
		File fApp = new File();
		fApp.setCode(codeConf);
		fApp.setName("/change/me");
		
		Dir dApp = new Dir();
		dApp.setCode(codeApp);
		dApp.getFile().add(fApp);
		
		
		dir.getDir().add(dApp);
		
		JaxbUtil.save(f, dir, true);
	}
}