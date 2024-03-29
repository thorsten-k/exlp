package org.exlp.model.xml.io;

import java.io.FileNotFoundException;

import org.exlp.test.ExlpBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPolicy extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestPolicy.class);
		
	@BeforeAll
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
    	xml.setId(1l);
    	xml.setCode("myCode");
    	xml.setGroup("Administrators");
    	xml.setUser("Administrator");
    	xml.setDescription("myDescription");
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
		ExlpBootstrap.init();	
			
		TestPolicy.initFiles();	
		TestPolicy test = new TestPolicy();
		test.save();
    }
}