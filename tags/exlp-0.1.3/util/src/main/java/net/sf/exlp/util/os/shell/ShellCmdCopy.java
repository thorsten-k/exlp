package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellCmdCopy extends AbstractShellCmd
{
	static Log logger = LogFactory.getLog(ShellCmdCopy.class);
	
	public ShellCmdCopy()
	{
		
	}
	
	public String copyFile(String from, String to) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(arch)
		{
			case Win32: sb.append("copy "+from+" "+to);break;
			case OsX:	sb.append("cp "+from+" "+to);break;
			default: errorUnsupportedOS("copy fileA to fileB");break;
		}	
		return sb.toString();
	}
}