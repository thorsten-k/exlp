package net.sf.exlp.util.io;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.interfaces.util.xml.JaxbInterface;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;
import net.sf.exlp.xml.xpath.IoXpath;

public class ExlpCentralConfigPointer
{
	final static Logger logger = LoggerFactory.getLogger(ExlpCentralConfigPointer.class);
	
	private final String appCode;
	private JaxbInterface jaxb; public ExlpCentralConfigPointer jaxb(JaxbInterface jaxb) {this.jaxb=jaxb; return this;}
	
	public static <E extends Enum<E>> ExlpCentralConfigPointer instance(E appCode) {return new ExlpCentralConfigPointer(appCode.toString());}
	private ExlpCentralConfigPointer(String appCode)
	{
		this.appCode = appCode;
	}
	
	public java.io.File toPointerFile() throws ExlpConfigurationException
	{
		java.io.File fHome = new java.io.File(System.getProperty("user.home"));
		java.io.File fMvn = new java.io.File(fHome,".m2");
		java.io.File fXml = new java.io.File(fMvn,"exlp.xml");
		java.io.File fJson = new java.io.File(fMvn,"exlp.json");
		if(!fHome.exists()) {throw new ExlpConfigurationException("Directory does not exist: "+fHome.getAbsolutePath());}
		if(!fMvn.exists()) {throw new ExlpConfigurationException("Directory does not exist: "+fMvn.getAbsolutePath());}
		
		if(fJson.exists()) {return fJson;}
		else if(fXml.exists()) {return fXml;}
		else {throw new ExlpConfigurationException("EXLP CCP does not exist in: "+fMvn.getAbsolutePath());}
	}
	
	public static java.io.File getFile(String appCode, String codeConf) throws ExlpConfigurationException
	{
		ExlpCentralConfigPointer ccp = new ExlpCentralConfigPointer(appCode);
		ccp.jaxb(JaxbUtil.instance());
		return ccp.toFile(codeConf);
//		java.io.File fHome = new java.io.File(System.getProperty("user.home"));
//		java.io.File fMvn = new java.io.File(fHome,".m2");
//		if(!fHome.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fHome.getAbsolutePath());}
//		if(!fMvn.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fMvn.getAbsolutePath());}
//		return ExlpCentralConfigPointer.getFile(fMvn, "exlp.xml", codeApp, codeConf);
	}
	
//	private static java.io.File resolveJsonFile(String codeApp, String codeConf) throws ExlpConfigurationException
//	{
//		java.io.File fHome = new java.io.File(System.getProperty("user.home"));
//		java.io.File fMvn = new java.io.File(fHome,".m2");
//		if(!fHome.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fHome.getAbsolutePath());}
//		if(!fMvn.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fMvn.getAbsolutePath());}
//		return ExlpCentralConfigPointer.getFile(fMvn, "exlp.xml", codeApp, codeConf);
//		throw new ExlpConfigurationException("Migration for native");
//	}
	
	public java.io.File toFile(String confCode) throws ExlpConfigurationException
	{
		java.io.File fHome = new java.io.File(System.getProperty("user.home"));
		java.io.File fMvn = new java.io.File(fHome,".m2");
		if(!fHome.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fHome.getAbsolutePath());}
		if(!fMvn.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fMvn.getAbsolutePath());}
		return this.getFile(fMvn, "exlp.xml", confCode);
	}
	
	private java.io.File getFile(java.io.File fDir, String fileName, String codeConf) throws ExlpConfigurationException
	{
		Dir dir;
		Dir dirApp;
		
		java.io.File f = new java.io.File(fDir,fileName);
		if(!f.exists())
		{
			logger.warn("ExLP Central Config Pointer does not exist ... creating dummy");
			create(f, appCode, codeConf);
			String errorMsg = "ExLP Central Config Pointer created. You have to edit this file, otherwise you will get a permanent error!";
			logger.warn(errorMsg);
			throw new ExlpConfigurationException(errorMsg);
		}
		
		try
		{
			logger.info("Loading "+f.getAbsolutePath()+" to "+Dir.class.getSimpleName());
			dir = jaxb.load(Dir.class,f.getAbsolutePath());
			System.out.println(Dir.class.getSimpleName()+" with "+dir.getDir().size()+" elements");
		}
		catch (FileNotFoundException e) {throw new ExlpConfigurationException(e.getMessage());}
		
		try{dirApp = IoXpath.getDir(dir, appCode);}
		catch (ExlpXpathNotFoundException e)
		{
			logger.warn("<dir> does not exist, creating dummy");
			appendDir(f, dir, appCode, codeConf);
			String errorMsg = "Dummy <dir> created. You have to edit the file ("+f.getAbsolutePath()+"), otherwise you will get a permanent error!";
			logger.warn(errorMsg);
			throw new ExlpConfigurationException(errorMsg);
		}
		catch (ExlpXpathNotUniqueException e) {throw new ExlpConfigurationException(e.getMessage());}
		
		try
		{
			File fXml = IoXpath.getFile(dirApp, codeConf);
			java.io.File fConf = new java.io.File(fXml.getName());
			if(!fConf.exists()){throw new ExlpConfigurationException("File ("+fConf.getAbsolutePath()+") does not exist for app="+appCode+" code="+codeConf+" in : "+f.getAbsolutePath());}
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