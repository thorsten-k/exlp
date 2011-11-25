package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdCopy extends AbstractShellCmd
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdCopy.class);
	
	public ShellCmdCopy()
	{
		
	}
	
	public String copyFile(String from, String to) throws ExlpUnsupportedOsException
	{
		System.out.println("arch:"+arch);
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