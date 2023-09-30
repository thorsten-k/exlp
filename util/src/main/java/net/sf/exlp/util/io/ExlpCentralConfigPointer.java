package net.sf.exlp.util.io;

import java.io.FileNotFoundException;
import java.util.Objects;

import org.exlp.model.xml.io.Dir;
import org.exlp.model.xml.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.interfaces.util.xml.JaxbInterface;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.xpath.IoXpath;

public class ExlpCentralConfigPointer
{
	final static Logger logger = LoggerFactory.getLogger(ExlpCentralConfigPointer.class);
	
	private final String appCode;
	private JaxbInterface jaxb; public ExlpCentralConfigPointer jaxb(JaxbInterface jaxb) {this.jaxb=jaxb; return this;}
	private java.io.File fPointer;
	
	public static <E extends Enum<E>> ExlpCentralConfigPointer instance(E appCode) {return new ExlpCentralConfigPointer(appCode.toString());}
	public static ExlpCentralConfigPointer instance(String appCode) {return new ExlpCentralConfigPointer(appCode);}
	private ExlpCentralConfigPointer(String appCode)
	{
		this.appCode = appCode;
	}
	
	private java.io.File toPointerFile() throws ExlpConfigurationException
	{
		if(Objects.isNull(fPointer))
		{
			java.io.File fHome = new java.io.File(System.getProperty("user.home"));
			java.io.File fMvn = new java.io.File(fHome,".m2");
			fPointer = new java.io.File(fMvn,"exlp.xml");
			if(!fHome.exists()) {throw new ExlpConfigurationException("Directory does not exist: "+fHome.getAbsolutePath());}
			if(!fMvn.exists()) {throw new ExlpConfigurationException("Directory does not exist: "+fMvn.getAbsolutePath());}
			logger.info("Instantiated with "+fPointer.getAbsolutePath());
		}
		
		return fPointer;
	}
	
	@Deprecated //Use ExlpCentralConfigPointer.instance(app).jaxb(JaxbUtil);
	public static java.io.File getFile(String appCode, String codeConf) throws ExlpConfigurationException
	{
		ExlpCentralConfigPointer ccp = new ExlpCentralConfigPointer(appCode);
		ccp.jaxb(JaxbUtil.instance());
		return ccp.toFile(codeConf);
	}
	
	public java.io.File toFile(String confCode) throws ExlpConfigurationException
	{
		return this.getFile(this.toPointerFile(),confCode);
	}
	
//	private java.io.File getFile(java.io.File fDir, String fileName, String codeConf) throws ExlpConfigurationException
//	{		
//		java.io.File f = new java.io.File(fDir,fileName);
//		return getFile(f,codeConf);
//	}
	
	private java.io.File getFile(java.io.File f, String codeConf) throws ExlpConfigurationException
	{
		Dir dir;
		Dir dirApp;
		
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
			dir = jaxb.load(Dir.class,f.getAbsolutePath());
			logger.trace(Dir.class.getSimpleName()+" with "+dir.getDir().size()+" elements");
		}
		catch (FileNotFoundException e) {throw new ExlpConfigurationException(e.getMessage());}
		
		try {dirApp = IoXpath.getDir(dir,appCode);}
		catch (ExlpXpathNotFoundException e)
		{
			logger.warn("<dir> with "+appCode+" does not exist, creating dummy");
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
			logger.trace("Using config: "+fConf.getAbsolutePath());
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