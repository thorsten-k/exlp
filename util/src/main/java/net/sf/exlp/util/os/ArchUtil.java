package net.sf.exlp.util.os;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArchUtil
{
	static Log logger = LogFactory.getLog(ArchUtil.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	public enum OsArch {Win32, OsX, Linux, Iphone};
	public static OsArch arch;
	
	public static String toArch()
	{
		if(arch==null){setArch();}
		return arch.toString().toLowerCase();
	}

	public static OsArch setArch()
	{
		if(SystemUtils.IS_OS_MAC_OSX){arch=OsArch.OsX;}
		else if(SystemUtils.IS_OS_WINDOWS){arch=OsArch.Win32;}
		else if(SystemUtils.IS_OS_LINUX){arch=OsArch.Linux;}
		else if(SystemUtils.OS_NAME.equals("Darwin")){arch=OsArch.Iphone;}
		else {errorUnsupportedOS();}
		return arch;
	}
	
	private static void errorUnsupportedOS()
	{
		logger.fatal("System "+ SystemUtils.OS_NAME +" not supported");
		logger.fatal("We need to now the following details:");
		logger.fatal("  -UserDocumentDir");
		logger.fatal("  -DesktopDir");
		logger.fatal("  -ConsoleCharSet");
		logger.fatal("  -ApplicationSettingsDir");
		System.exit(-1);
	}
	
	public static String getDocDir()
	{
		if(arch==null){setArch();}
		StringBuffer sb = new StringBuffer();
		sb.append(SystemUtils.getUserHome());
		switch(arch)
		{
			case Win32: sb.append(SystemUtils.FILE_SEPARATOR+"Eigene Dateien");break;
			case OsX:	sb.append(SystemUtils.FILE_SEPARATOR+"Documents");break;
			case Linux: sb.append(SystemUtils.FILE_SEPARATOR+"Documents");break;
			default:	errorUnsupportedOS();break;
		}	
		return sb.toString();
	}
	
	public static String getAppSettingsDir(String appName)
	{
		if(arch==null){setArch();}
		StringBuffer sb = new StringBuffer();
		sb.append(SystemUtils.getUserHome());
		switch(arch)
		{
			case Win32: sb=new StringBuffer();
						sb.append("C:"+SystemUtils.FILE_SEPARATOR+SystemUtils.FILE_SEPARATOR+appName);
//						sb.append(System.getenv("APPDATA"));
//						sb.append(SystemUtils.FILE_SEPARATOR+appName);
						break;
			case OsX:	sb.append(SystemUtils.FILE_SEPARATOR+"Library");
//						sb.append(SystemUtils.FILE_SEPARATOR+"Application Support");
						sb.append(SystemUtils.FILE_SEPARATOR+appName);break;
			case Linux: sb.append(SystemUtils.FILE_SEPARATOR+"."+appName);break;
			default:	errorUnsupportedOS();break;
		}
		return sb.toString();
	}
	
	public static String getConsoleCharSet()
	{
		if(arch==null){setArch();}
		StringBuffer sb = new StringBuffer();
		sb.append(SystemUtils.getUserHome());
		switch(arch)
		{
			case Win32: sb.append("cp850");break;
			default:	errorUnsupportedOS();break;
		}	
		return sb.toString();
	}
	
	public static String getDesktopDir()
	{
		if(arch==null){setArch();}
		StringBuffer sb = new StringBuffer();
		sb.append(System.getProperty("user.home"));
		switch(arch)
		{
			case Win32: sb.append(SystemUtils.FILE_SEPARATOR+"Desktop");break;
			case OsX:	sb.append(SystemUtils.FILE_SEPARATOR+"Desktop");break;
			default:	errorUnsupportedOS();break;
		}	
		return sb.toString();
	}
	
	public static String getClassPath(List<String> newLibs, String baseDir)
	{
		if(arch==null){setArch();}
		String cSep ="";
		switch(arch)
		{
			case Win32: cSep=";";break;
			case OsX:	cSep=":";break;
			case Linux:	cSep=":";break;
			default:	errorUnsupportedOS();break;
		}
		
		StringBuffer sb = new StringBuffer();
		boolean nextLib = false;
		for(String lib : newLibs)
		{
			if(nextLib){sb.append(cSep);}
			sb.append(baseDir);
			sb.append(fs);
			sb.append(lib);
			nextLib=true;
		}
		return sb.toString();
	}
	
	public static boolean isAbsolute(String filePath)
	{
		if(arch==null){setArch();}
		boolean isAbsolute = false;
		switch(arch)
		{
			case OsX:	if(filePath.substring(0,1).equals("/")){isAbsolute=true;};break;
			case Linux:	if(filePath.substring(0,1).equals("/")){isAbsolute=true;};break;
			case Win32: Pattern p = Pattern.compile("([a-zA-Z]):(/\\\\)*(.*)");
						Matcher m=p.matcher(filePath);
						if(m.matches()){isAbsolute=true;};break;
			default:	errorUnsupportedOS();break;
		}	
		return isAbsolute;
	}
	
	public static boolean isUnixLike()
	{
		if(arch==null){setArch();}
		boolean isUnixLike = false;
		switch(arch)
		{
			case OsX:	isUnixLike=true;break;
			case Linux:	isUnixLike=true;break;
			case Win32: isUnixLike=false;break;
			default:	errorUnsupportedOS();break;
		}	
		return isUnixLike;
	}
}
