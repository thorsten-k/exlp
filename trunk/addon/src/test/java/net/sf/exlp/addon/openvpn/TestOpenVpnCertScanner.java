package net.sf.exlp.addon.openvpn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import net.sf.exlp.addon.openvpn.event.OpenVpnCertEvent;
import net.sf.exlp.test.AbstractExlpAddonTst;
import net.sf.exlp.test.ExlpAddonTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.identity.Certificate;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOpenVpnCertScanner extends AbstractExlpAddonTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpAddonTst.class);
	
	private static final String dirSingle = "src/test/resources/data/openvpn/single";
	private static final String dirMulti = "src/test/resources/data/openvpn/multi";
	
	private static File fXml;
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File("src/test/resources/data/openvpn/single.xml");
	}
    
    @Test
    public void testSingle() throws FileNotFoundException 
    {
    	process(false);
    }
    
    private void process(boolean save) throws FileNotFoundException
    {
    	OpenVpnDirScanner ods = new OpenVpnDirScanner();
		List<OpenVpnCertEvent> list = ods.getCertEvents(dirSingle);
		
		Assert.assertEquals(1, list.size());
		Certificate test = list.get(0).getCertificate();
		
		if(save)
		{
			JaxbUtil.save(fXml, test, true);
		}
		else
		{
			Certificate ref = (Certificate)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Certificate.class);
			assertJaxbEquals(ref, test);
		}
    }
    
    @Test
    public void testMulti() throws FileNotFoundException 
    {
    	OpenVpnDirScanner ods = new OpenVpnDirScanner();
		List<OpenVpnCertEvent> list = ods.getCertEvents(dirMulti);
		
		Assert.assertEquals(2, list.size());
    }
	
	public static void main(String[] args) throws FileNotFoundException
    {
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());
		ExlpAddonTestBootstrap.init();
					
		TestOpenVpnCertScanner.initFiles();
		TestOpenVpnCertScanner test = new TestOpenVpnCertScanner();
		test.process(true);
    }
}