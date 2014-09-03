package net.sf.exlp.shell.cmd;

import java.io.File;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdChmod
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdChmod.class);
	
	public static synchronized String executeable(File f) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case OsX: 	 sb.append(executeableOsx(f));break;
			default: OsArchitectureUtil.errorUnsupportedOS("EXPORT xxx = yyy");break;
		}	
		return sb.toString();
	}
	
	private static String executeableOsx(File f)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("chmod +x ").append(f.getAbsolutePath());
		
		return sb.toString();
	}
}