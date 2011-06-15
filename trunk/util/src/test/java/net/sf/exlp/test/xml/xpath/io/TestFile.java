package net.sf.exlp.test.xml.xpath.io;

import java.io.FileNotFoundException;
import java.util.Date;

import net.sf.exlp.test.AbstractExlpTest;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.File;
import net.sf.exlp.xml.io.Files;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFile extends AbstractExlpTest
{
	static Log logger = LogFactory.getLog(TestFile.class);
	
	private static final String rootDir = "src/test/resources/data/xml/io";
	
	private static java.io.File fFile, fFiles;
	
	@BeforeClass
	public static void initFiles()
	{
		fFile = new java.io.File(rootDir,"file.xml");
		fFiles = new java.io.File(rootDir,"files.xml");
	}
    
    @Test
    public void testFile() throws FileNotFoundException
    {
    	File xmlTest = createFile();
    	File xmlRef = (File)JaxbUtil.loadJAXB(fFile.getAbsolutePath(), File.class);
    	Assert.assertEquals(JaxbUtil.toString(xmlRef),JaxbUtil.toString(xmlTest));
    }
    
    @Test
    public void testFiles() throws FileNotFoundException
    {
    	Files xmlTest = createFiles();
    	Files xmlRef = (Files)JaxbUtil.loadJAXB(fFiles.getAbsolutePath(), Files.class);
    	Assert.assertEquals(JaxbUtil.toString(xmlRef),JaxbUtil.toString(xmlTest));
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	File xml = createFile();
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fFile, xml, new ExlpNsPrefixMapper(), true);
    	
    	Files xmlFiles = createFiles();
    	JaxbUtil.debug2(this.getClass(),xmlFiles, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fFiles, xmlFiles, new ExlpNsPrefixMapper(), true);
    }

    public static Files createFiles()
    {   	
    	Files xml = new Files();
    	xml.getFile().add(createFile());
    	xml.getFile().add(createFile());
    	return xml;
    }
    
    public static File createFile()
    {
    	Date d = DateUtil.getDateFromInt(2012, 1, 1,10,10,10);
    	
    	File xml = new File();
    	xml.setId(1);
    	xml.setCode("code");
    	xml.setName("test.txt");
    	xml.setLastModifed(DateUtil.getXmlGc4D(d));
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