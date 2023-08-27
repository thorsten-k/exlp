package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;

import org.exlp.model.xml.io.Files;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlFiles extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFiles.class);
	
	@BeforeClass public static void initFiles(){fXml = new java.io.File(rootDir,Files.class.getSimpleName()+".xml");}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	Files test = create(true);
    	Files ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Files.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Files create(boolean withChilds)
    {
    	Files xml = new Files();
    	xml.getFile().add(TestXmlFile.create(false));
    	xml.getFile().add(TestXmlFile.create(false));
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		ExlpTstBootstrap.init();
			
		TestXmlFiles.initFiles();	
		TestXmlFiles test = new TestXmlFiles();
		test.save();
    }
}