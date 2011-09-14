package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPolicy extends AbstractIoXmlTest
{
	static Log logger = LogFactory.getLog(TestPolicy.class);
		
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"policy.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Policy test = createPolicy();
    	Policy ref = (Policy)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Policy.class);
    	assertJaxbEquals(ref, test);
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Policy xml = createPolicy();
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fXml, xml, new ExlpNsPrefixMapper(), true);
    }
    
    public static Policy createPolicy()
    {
    	Policy xml = new Policy();
    	xml.setId(1);
    	xml.setCode("myCode");
    	xml.setSys("Administrators");
    	xml.setName("myName");
    	xml.getAcl().add(TestAcl.createAcl());
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestPolicy.initFiles();	
		TestPolicy test = new TestPolicy();
		test.save();
    }
}