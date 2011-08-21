package net.sf.exlp.util.io;

import java.io.FileNotFoundException;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExlpCentralConfigPointer
{
	static Log logger = LogFactory.getLog(ExlpCentralConfigPointer.class);
	
	public static boolean getFile(String codeApp) throws ExlpConfigurationException
	{
		return getFile(codeApp, "root");
	}
	
	public static boolean getFile(String codeApp, String codeConf) throws ExlpConfigurationException
	{
		java.io.File fHome = new java.io.File(System.getProperty("user.home"));
		if(!fHome.exists()){throw new ExlpConfigurationException("Directory does not exist: "+fHome.getAbsolutePath());}
		java.io.File fMvn = new java.io.File(fHome,".m2");
		
		return getFile(fMvn, "exlp.xml", codeApp, codeConf);
	}
	
	public static boolean getFile(java.io.File fDir, String fileName, String codeApp, String codeConf) throws ExlpConfigurationException
	{
		Dir dir;
		java.io.File f = new java.io.File(fDir,fileName);
		if(!f.exists())
		{
			logger.warn("ExLP Central Config Pointer does not exist ... creating dummy");
			create(f, codeApp, codeConf);
			String errorMsg = "ExLP Central Config Pointer created. You have to edit this file, otherwise you will get a permanent error!";
			logger.warn(errorMsg);
			throw new ExlpConfigurationException(errorMsg);
		}
		
		try {dir = (Dir)JaxbUtil.loadJAXB(f.getAbsolutePath(), Dir.class);}
		catch (FileNotFoundException e) {throw new ExlpConfigurationException(e.getMessage());}
		
//		Dir dApp = 
		
		return true;
	}
	
	private static void create(java.io.File f, String codeApp, String codeConf)
	{		
		File fApp = new File();
		fApp.setCode(codeConf);
		fApp.setName("/change/me");
		
		
		Dir dApp = new Dir();
		dApp.setCode(codeApp);
		dApp.getFile().add(fApp);
		
		Dir dir = new Dir();
		dir.getDir().add(dApp);
		
		JaxbUtil.save(f, dir, true);
	}
}