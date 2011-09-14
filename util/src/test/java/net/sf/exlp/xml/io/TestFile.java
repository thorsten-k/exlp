package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFile extends AbstractIoXmlTest
{
	static Log logger = LogFactory.getLog(TestFile.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new java.io.File(rootDir,"file.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	File test = createFile();
    	File ref = (File)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), File.class);
    	assertJaxbEquals(ref, test);
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	File xml = createFile();
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fXml, xml, new ExlpNsPrefixMapper(), true);
    }

    public static List<File> createFiles()
    {   	
    	List<File> list = new ArrayList<File>();
    	list.add(createFile());
    	list.add(createFile());
    	return list;
    }
    
    public static File createFile()
    {
    	Date d = DateUtil.getDateFromInt(2012, 1, 1,10,10,10);
    	
    	File xml = new File();
    	xml.setId(1);
    	xml.setCode("code");
    	xml.setName("test.txt");
    	xml.setLastModifed(DateUtil.getXmlGc4D(d));
    	xml.getPolicy().add(TestPolicy.createPolicy());
    	return xml;
    }
	
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