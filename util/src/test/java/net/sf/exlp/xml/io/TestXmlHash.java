package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;

import org.exlp.model.xml.io.Hash;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlHash extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHash.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,Hash.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Hash test = create(true);
    	Hash ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Hash.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Hash create(boolean withChilds)
    {
    	Hash xml = new Hash();
    	xml.setValue("myValue");

    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		ExlpTstBootstrap.init();
			
		TestXmlHash.initFiles();	
		TestXmlHash test = new TestXmlHash();
		test.save();
    }
}