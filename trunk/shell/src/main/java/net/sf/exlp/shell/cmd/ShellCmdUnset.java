package net.sf.exlp.shell.cmd;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdUnset
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdUnset.class);
	
	public static synchronized String unset(String variable) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case OsX: 	 sb.append(exportOsx(variable));break;
			default: OsArchitectureUtil.errorUnsupportedOS("unset xxx");break;
		}	
		return sb.toString();
	}
	
	private static String exportOsx(String variable)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("unset ").append(variable);
		
		return sb.toString();
	}
}