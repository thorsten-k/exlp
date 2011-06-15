package net.sf.exlp.test.xml.xpath.io;

import java.io.FileNotFoundException;
import java.util.Date;

import net.sf.exlp.test.AbstractExlpTest;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.Dirs;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDir extends AbstractExlpTest
{
	static Log logger = LogFactory.getLog(TestDir.class);
	
	private static final String rootDir = "src/test/resources/data/xml/io";
	
	private static java.io.File fDir, fDirs, fComplex;
	
	@BeforeClass
	public static void initFiles()
	{
		fDir = new java.io.File(rootDir,"dir.xml");
		fDirs = new java.io.File(rootDir,"dirs.xml");
		fComplex = new java.io.File(rootDir,"complex.xml");
	}
    
    @Test
    public void testDir() throws FileNotFoundException
    {
    	Dir xmlTest = createDir(false,false);
    	Dir xmlRef = (Dir)JaxbUtil.loadJAXB(fDir.getAbsolutePath(), Dir.class);
    	Assert.assertEquals(JaxbUtil.toString(xmlRef),JaxbUtil.toString(xmlTest));
    }
    
    @Test
    public void testDirs() throws FileNotFoundException
    {
    	Dirs xmlTest = createDirs(false,false);
    	Dirs xmlRef = (Dirs)JaxbUtil.loadJAXB(fDirs.getAbsolutePath(), Dirs.class);
    	Assert.assertEquals(JaxbUtil.toString(xmlRef),JaxbUtil.toString(xmlTest));
    }
    
    @Test
    public void testComplex() throws FileNotFoundException
    {
    	Dir xmlTest = createDir(true,true);
    	Dir xmlRef = (Dir)JaxbUtil.loadJAXB(fComplex.getAbsolutePath(), Dir.class);
    	Assert.assertEquals(JaxbUtil.toString(xmlRef),JaxbUtil.toString(xmlTest));
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Dir xml = createDir(false,false);
    	JaxbUtil.debug2(this.getClass(),xml, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fDir, xml, new ExlpNsPrefixMapper(), true);
    	
    	Dirs xmlFiles = createDirs(false,false);
    	JaxbUtil.debug2(this.getClass(),xmlFiles, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fDirs, xmlFiles, new ExlpNsPrefixMapper(), true);
    	
    	Dir xmlComplex = createDir(true,true);
    	JaxbUtil.debug2(this.getClass(),xmlComplex, new ExlpNsPrefixMapper());
    	JaxbUtil.save(fComplex, xmlComplex, new ExlpNsPrefixMapper(), true);
    }

    public static Dirs createDirs(boolean withFiles, boolean withDirs)
    {   	
    	Dirs xml = new Dirs();
    	xml.getDir().add(createDir(withFiles,withDirs));
    	xml.getDir().add(createDir(withFiles,withDirs));
    	return xml;
    }
    
    public static Dir createDir(boolean withFiles,boolean withDirs)
    {
    	Date d = DateUtil.getDateFromInt(2012, 1, 1,10,10,10);
    	
    	Dir xml = new Dir();
    	xml.setId(1);
    	xml.setCode("code");
    	xml.setName("test.txt");
    	xml.setAllowCreate(true);
    	xml.setLastModifed(DateUtil.getXmlGc4D(d));
    	
    	if(withFiles){xml.setFiles(TestFile.createFiles());}
    	if(withDirs){xml.setDirs(TestDir.createDirs(true,false));}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestDir.initFiles();	
		TestDir test = new TestDir();
		test.save();
    }
}