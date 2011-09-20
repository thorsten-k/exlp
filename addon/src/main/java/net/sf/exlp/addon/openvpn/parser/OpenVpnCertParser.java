package net.sf.exlp.addon.openvpn.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import net.sf.exlp.addon.openvpn.event.OpenVpnCertEvent;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.xml.identity.Certificate;

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
		logger.debug("Processing "+f.getAbsolutePath());
		OpenVpnCertEvent cert = null;
		try
		{
			FileInputStream fis = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
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
		Certificate cert = new Certificate();
		cert.setNotAfter(DateUtil.getXmlGc4D(x509Cert.getNotAfter(),true));
		cert.setNotBefore(DateUtil.getXmlGc4D(x509Cert.getNotBefore(),true));
		cert.setSerial(x509Cert.getSerialNumber().intValue());
		cert.setEmail(getEmail(x509Cert.getSubjectDN().getName()));
		cert.setCn(getCn(x509Cert.getSubjectDN().getName()));
		logger.debug(x509Cert.getSubjectDN().getName());
		
		OpenVpnCertEvent event = new OpenVpnCertEvent(cert);
		return event;
	}
	
	private String getCn(String dn)
	{
		int beginIndex = dn.indexOf("CN=");
		dn=dn.substring(beginIndex,dn.length());
		int endIndex = dn.indexOf(",");
		String email = dn.substring(3,endIndex);
		return email;
	}
	
	private String getEmail(String dn)
	{
		int beginIndex = "EMAILADDRESS=".length();
		int endIndex = dn.indexOf(",");
		String email = dn.substring(beginIndex,endIndex).toLowerCase();
		return email;
	}
}