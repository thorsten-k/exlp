package net.sf.exlp.addon.openvpn.facade.bean;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.exlp.addon.common.data.facade.bean.AbstractExlpFacadeBean;
import net.sf.exlp.addon.openvpn.ejb.OpenVpnCert;
import net.sf.exlp.addon.openvpn.facade.exlp.ExlpOpenVpnFacade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateful
@Remote
public class ExlpOpenVpnFacadeBean extends AbstractExlpFacadeBean implements ExlpOpenVpnFacade, Serializable
{
	static Log logger = LogFactory.getLog(ExlpOpenVpnFacadeBean.class);
	static final long serialVersionUID=10;
	
	public OpenVpnCert fOpenVpnCert(int serial)
	{
		OpenVpnCert cert = null;
		Query nq = getManager().createNamedQuery("fOpenVpnCert");
			nq.setParameter("serial", serial);
		try	{cert=(OpenVpnCert)nq.getSingleResult();}
		catch (NoResultException ex){}
		return cert;
	}
	
	@Remove
	public void checkout(){logger.debug("Checkout");}	
}