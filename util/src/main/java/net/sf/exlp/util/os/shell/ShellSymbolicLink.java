package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.os.ArchUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellSymbolicLink extends AbstractShellCmd
{
	final static Logger logger = LoggerFactory.getLogger(ShellSymbolicLink.class);
	
	private ShellSymbolicLink()
	{
		
	}
	
	public static String symbolicLink(String existing, String link) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(ArchUtil.getArch())
		{
			case OsX:	sb.append("ln -s "+existing+" "+link);break;
			case Linux:	sb.append("ln -s "+existing+" "+link);break;
			default:	errorUnsupportedOS("print routingtable");break;
		}	
		return sb.toString();
	}

}
