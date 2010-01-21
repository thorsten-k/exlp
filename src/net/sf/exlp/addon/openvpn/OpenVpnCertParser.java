package net.sf.exlp.addon.openvpn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import net.sf.exlp.addon.openvpn.ejb.OpenVpnCert;
import net.sf.exlp.addon.openvpn.event.OpenVpnCertEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pk.edu.niit.clarens.util.PEMBlock;

public class OpenVpnCertParser
{	
	static Log logger = LogFactory.getLog(OpenVpnCertParser.class);
		
	public OpenVpnCertParser()
	{

	}
	
	public OpenVpnCertEvent getCert(File f)
	{
		OpenVpnCertEvent cert = null;
		try
		{
			FileInputStream fis = new FileInputStream(f);
			BufferedReader br = new BufferedReader( new InputStreamReader(fis));
			PEMBlock pem = PEMBlock.getInstance(br);
			X509Certificate x509Cert = (X509Certificate)pem.getCertificate();
			cert = getOpenVpnCert(x509Cert);
		}
		catch (FileNotFoundException e) {logger.error(e);}
		catch (CertificateException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
		return cert;
	}
	
	private OpenVpnCertEvent getOpenVpnCert(X509Certificate x509Cert)
	{
		OpenVpnCert ovpnCert = new OpenVpnCert();
		ovpnCert.setNotAfter(x509Cert.getNotAfter());
		ovpnCert.setNotBefore(x509Cert.getNotBefore());
		ovpnCert.setSerial(x509Cert.getSerialNumber().intValue());
		ovpnCert.setDn(x509Cert.getSubjectDN().getName());
		
		OpenVpnCertEvent cert = new OpenVpnCertEvent(ovpnCert,getEmail(ovpnCert));
		return cert;
	}
	
	private String getEmail(OpenVpnCert cert)
	{
		String dn = cert.getDn();
		int beginIndex = "EMAILADDRESS=".length();
		int endIndex = dn.indexOf(",");
		String email = dn.substring(beginIndex,endIndex).toLowerCase();
		return email;
	}
}