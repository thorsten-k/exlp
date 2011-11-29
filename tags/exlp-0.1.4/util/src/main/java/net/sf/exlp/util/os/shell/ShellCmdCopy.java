package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.os.ArchUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdCopy extends AbstractShellCmd
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdCopy.class);
	
	private ShellCmdCopy()
	{
		
	}
	
	public static String copyFile(String from, String to) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(ArchUtil.getArch())
		{
			case Win32: sb.append("copy "+from+" "+to);break;
			case OsX:	sb.append("cp "+from+" "+to);break;
			default: errorUnsupportedOS("copy fileA to fileB");break;
		}	
		return sb.toString();
	}
}