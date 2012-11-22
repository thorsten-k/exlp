package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFile extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestFile.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"file.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	File test = create();
    	File ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), File.class);
    	assertJaxbEquals(ref, test);
    }

    public static List<File> createFiles()
    {   	
    	List<File> list = new ArrayList<File>();
    	list.add(create());
    	list.add(create());
    	return list;
    }
    
    private static File create(){return create(false);}
    public static File create(boolean withChilds)
    {
    	Date d = DateUtil.getDateFromInt(2012, 1, 1,10,10,10);
    	
    	File xml = new File();
    	xml.setId(1);
    	xml.setCode("code");
    	xml.setName("test.txt");
    	xml.setSize(123);
    	xml.setMime("myMime");
    	xml.setLastModifed(DateUtil.getXmlGc4D(d));
    	
    	if(withChilds)
    	{
    		xml.getPolicy().add(TestPolicy.create(false));
    		xml.getPolicy().add(TestPolicy.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestFile.initFiles();	
		TestFile test = new TestFile();
		test.save();
    }
}