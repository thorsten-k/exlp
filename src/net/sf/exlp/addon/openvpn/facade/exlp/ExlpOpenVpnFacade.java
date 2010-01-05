package net.sf.exlp.addon.openvpn.facade.exlp;

import net.sf.exlp.addon.common.facade.exlp.ExlpFacade;
import net.sf.exlp.addon.openvpn.ejb.OpenVpnCert;

public interface ExlpOpenVpnFacade extends ExlpFacade
{
	OpenVpnCert fOpenVpnCert(int serial);
}
