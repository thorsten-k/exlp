package net.sf.exlp.addon.openvpn.event;

import net.sf.exlp.addon.openvpn.ejb.OpenVpnCert;
import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.log4j.Logger;

public class OpenVpnCertEvent extends AbstractEvent implements LogEvent
{
	static Logger logger = Logger.getLogger(OpenVpnCertEvent.class);
	static final long serialVersionUID=1;
	
	private OpenVpnCert cert;
	private String email;

	public OpenVpnCertEvent(OpenVpnCert cert, String email)
	{
		this.cert=cert;
		this.email=email;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("** dn\t"+cert.getDn());
		logger.debug("** email\t"+email);
	}
	
	public OpenVpnCert getCert() {return cert;}
	public void setCert(OpenVpnCert cert) {this.cert = cert;}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
}