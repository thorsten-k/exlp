package net.sf.exlp.addon.openvpn;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.test.AbstractExlpAddonTest;
import net.sf.exlp.test.ExlpAddonTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestOpenVpnCertScanner extends AbstractExlpAddonTest
{
	static Log logger = LogFactory.getLog(TestOpenVpnCertScanner.class);
	
	private static File fPlain;
	
	@BeforeClass
	public static void initFiles()
	{

	}
    
    @Test
    public void testPlain() throws FileNotFoundException
    {
    	
    }
	
	public static void main(String[] args)
    {
		ExlpAddonTstBootstrap.init();
			
//		TestOpenVpnCertScanner test = new TestOpenVpnCertScanner();
//		test.save();
    }
}