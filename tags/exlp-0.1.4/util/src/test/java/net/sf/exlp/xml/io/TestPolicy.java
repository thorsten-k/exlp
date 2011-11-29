package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPolicy extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestPolicy.class);
		
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"policy.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Policy test = create();
    	Policy ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Policy.class);
    	assertJaxbEquals(ref, test);
    }
    
    private static Policy create(){return create(true);}
    public static Policy create(boolean withChilds)
    {
    	Policy xml = new Policy();
    	xml.setId(1);
    	xml.setCode("myCode");
    	xml.setSys("Administrators");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
    		xml.getAcl().add(TestAcl.create());
    		xml.getAcl().add(TestAcl.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
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