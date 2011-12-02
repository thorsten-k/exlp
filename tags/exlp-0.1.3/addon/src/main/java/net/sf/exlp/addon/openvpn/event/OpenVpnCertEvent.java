package net.sf.exlp.addon.openvpn.event;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;
import net.sf.exlp.xml.identity.Certificate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OpenVpnCertEvent extends AbstractEvent implements LogEvent
{
	static Log logger = LogFactory.getLog(OpenVpnCertEvent.class);
	static final long serialVersionUID=1;
	
	private Certificate cert;

	public OpenVpnCertEvent(Certificate cert)
	{
		this.cert=cert;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("** email\t"+cert.getEmail());
	}
	
	public Certificate getCertificate() {return cert;}
	public void setCertificate(Certificate cert) {this.cert = cert;}
}