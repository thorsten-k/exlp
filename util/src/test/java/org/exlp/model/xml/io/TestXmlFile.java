package org.exlp.model.xml.io;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.exlp.test.ExlpBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.exlp.util.system.DateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFile extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFile.class);
	
	@BeforeAll
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"file.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	File test = create(true);
    	File ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), File.class);
    	assertJaxbEquals(ref, test);
    }

    public static List<File> createFiles()
    {   	
    	List<File> list = new ArrayList<File>();
    	list.add(create(false));
    	list.add(create(false));
    	return list;
    }
    
    public static File create(boolean withChilds)
    {
    	Date d = DateUtil.toDate(LocalDateTime.of(2012,1,1,10,10,10));
    	
    	File xml = new File();
    	xml.setId(1l);
    	xml.setCode("code");
    	xml.setName("test.txt");
    	xml.setSize(123l);
    	xml.setMime("myMime");
    	xml.setSymbol("mySymbol");
    	xml.setCategory("myCategory");
    	xml.setLastModifed(DateUtil.toXmlGc(d));

    	if(withChilds)
    	{
    		xml.setData(TestXmlData.create(false));
    		xml.getPolicy().add(TestPolicy.create(false));
    		xml.getPolicy().add(TestPolicy.create(false));
    		xml.setHash(TestXmlHash.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		ExlpBootstrap.init();
			
		TestXmlFile.initFiles();	
		TestXmlFile test = new TestXmlFile();
		test.save();
    }
}