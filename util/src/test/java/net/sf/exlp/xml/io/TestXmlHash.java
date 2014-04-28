package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
		
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());	
			
		TestXmlHash.initFiles();	
		TestXmlHash test = new TestXmlHash();
		test.save();
    }
}