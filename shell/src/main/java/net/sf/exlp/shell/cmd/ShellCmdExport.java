package net.sf.exlp.shell.cmd;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdExport
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdExport.class);
	
	public static synchronized String export(String variable, String value) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case OsX: 	 sb.append(exportOsx(variable, value));break;
			case Win32:	 sb.append(exportWindows(variable, value));break;
			default: OsArchitectureUtil.errorUnsupportedOS("EXPORT xxx = yyy");break;
		}	
		return sb.toString();
	}
	
	private static String exportOsx(String variable, String value)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("export ").append(variable).append("=").append(value);
		return sb.toString();
	}
	
	private static String exportWindows(String variable, String value)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SET ").append(variable).append("=").append(value);	
		return sb.toString();
	}
}