package org.exlp.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;
import net.sf.exlp.shell.os.OsArchitectureUtil.OsArch;

public class ShellCmdBash
{
	final static  Logger logger = LoggerFactory.getLogger(ShellCmdBash.class);
	
	private OsArch architecture; public ShellCmdBash architecture(OsArch architecture) {this.architecture = architecture; return this;}
	
	public static ShellCmdBash instance() {return new ShellCmdBash();}
	private ShellCmdBash() {this(OsArchitectureUtil.getArch());}
	private ShellCmdBash(OsArch architecture)
	{
		this.architecture = architecture;
	}
	
	public String echoOff() throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		
		switch(architecture)
		{
			case Win32: sb.append(" "); break;
			default:	;break;
		}
		
		return sb.toString();
	}
}