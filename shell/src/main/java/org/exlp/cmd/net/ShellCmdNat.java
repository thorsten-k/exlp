package org.exlp.cmd.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;
import net.sf.exlp.shell.os.OsArchitectureUtil.OsArch;

public class ShellCmdNat
{
	final static  Logger logger = LoggerFactory.getLogger(ShellCmdNat.class);
	
	private OsArch architecture; public ShellCmdNat architecture(OsArch architecture) {this.architecture = architecture; return this;}
	
	public static ShellCmdNat instance() {return new ShellCmdNat();}
	private ShellCmdNat() {this(OsArchitectureUtil.getArch());}
	private ShellCmdNat(OsArch architecture)
	{
		this.architecture = architecture;
	}
	
	public String nat(String listenAddress, Integer listenPort, String connectAddress, Integer connectPort) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		
		switch(architecture)
		{
			case Win32: this.win32(sb,listenPort,listenAddress,connectPort,connectAddress); break;
//			case OsX:	sb.append("ping -c "+anzahl+" "+host);break;
//			case Linux:	sb.append("ping -c "+anzahl+" "+host);break;
			default:	OsArchitectureUtil.errorUnsupportedOS("NAT");break;
		}
		
		return sb.toString();
	}
	
	
	
	public void win32(StringBuilder sb, Integer listenPort, String listenAddress, Integer connectPort, String connectAddress)
	{
		sb.append("netsh interface portproxy add v4tov4");
		sb.append(" listenport=").append(listenPort);
		sb.append(" listenaddress=").append(listenAddress);
		sb.append(" connectport=").append(connectPort);
		sb.append(" connectaddress=").append(connectAddress);
	}
	
	public String reset() throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		
		switch(architecture)
		{
			case Win32: sb.append("netsh interface portproxy reset"); break;
//			case OsX:	sb.append("ping -c "+anzahl+" "+host);break;
//			case Linux:	sb.append("ping -c "+anzahl+" "+host);break;
			default:	OsArchitectureUtil.errorUnsupportedOS("NAT");break;
		}
		
		return sb.toString();
	}
}
