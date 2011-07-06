package net.sf.exlp.test.xml.io;

import java.io.FileNotFoundException;

import net.sf.exlp.test.AbstractExlpTest;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Policy;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPolicy extends AbstractExlpTest
{
	static Log logger = LogFactory.getLog(TestPolicy.class);
	
	private static final String rootDir = "src/test/resources/data/xml/io";
	
	private static java.io.File fPolicy;
	
	@BeforeClass
	public static void initFiles()
	{
		fPolicy = new java.io.File(rootDir,"policy.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Policy test = createPolicy();
    	Policy ref = (Policy)JaxbUtil.loadJAXB(fPolicy.getAbsolutePath(), Policy.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Policy xml = createPolicy();
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fPolicy, xml, new ExlpNsPrefixMapper(), true);
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