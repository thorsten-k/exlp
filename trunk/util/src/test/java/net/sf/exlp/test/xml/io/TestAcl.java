package net.sf.exlp.test.xml.io;

import java.io.FileNotFoundException;

import net.sf.exlp.test.AbstractExlpTest;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Acl;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAcl extends AbstractExlpTest
{
	static Log logger = LogFactory.getLog(TestAcl.class);
	
	private static final String rootDir = "src/test/resources/data/xml/io";
	
	private static java.io.File fAcl;
	
	@BeforeClass
	public static void initFiles()
	{
		fAcl = new java.io.File(rootDir,"acl.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Acl test = createAcl();
    	Acl ref = (Acl)JaxbUtil.loadJAXB(fAcl.getAbsolutePath(), Acl.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Acl xml = createAcl();
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fAcl, xml, new ExlpNsPrefixMapper(), true);
    	
    }
    
    public static Acl createAcl()
    {
    	Acl xml = new Acl();
    	xml.setId(1);
    	xml.setPermission("r");
    	xml.setPass(true);
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestAcl.initFiles();	
		TestAcl test = new TestAcl();
		test.save();
    }
}