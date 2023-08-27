package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;

import org.exlp.model.xml.io.Policy;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

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
		ExlpTstBootstrap.init();	
			
		TestPolicy.initFiles();	
		TestPolicy test = new TestPolicy();
		test.save();
    }
}