package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellCmdMove extends AbstractShellCmd
{
	static Log logger = LogFactory.getLog(ShellCmdMove.class);
	
	public ShellCmdMove()
	{
		
	}
	
	public String moveFile(String from, String to) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(arch)
		{
			case Win32: sb.append("move "+from+" "+to);break;
			case OsX: sb.append("mv "+from+" "+to);break;
			default: errorUnsupportedOS("mv fileA to fileB");break;
		}	
		return sb.toString();
	}
}