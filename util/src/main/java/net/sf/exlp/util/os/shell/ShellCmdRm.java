package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellCmdRm extends AbstractShellCmd
{
	static Log logger = LogFactory.getLog(ShellCmdRm.class);
	
	public ShellCmdRm()
	{
		
	}
	
	public String rmDir(String dir, boolean onlySubEntries) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(arch)
		{
			case Win32:  sb.append(rmDirWin(dir, onlySubEntries));break;
			default: errorUnsupportedOS("copy fileA to fileB");break;
		}	
		return sb.toString();
	}
	
	private String rmDirWin(String dir, boolean onlySubEntries)
	{
		StringBuffer sb = new StringBuffer();
		if(onlySubEntries)
		{
			sb.append("del");
			sb.append(" /Q");
			sb.append(" /S");
		}
		else
		{
			sb.append("rmdir");
		}
		sb.append(" ").append(dir);
		return sb.toString();
	}
}