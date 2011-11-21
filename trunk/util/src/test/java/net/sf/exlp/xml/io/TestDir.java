package net.sf.exlp.xml.io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDir extends AbstractIoXmlTest
{
	static Log logger = LogFactory.getLog(TestDir.class);
	
	private static java.io.File fDir, fComplex;
	
	@BeforeClass
	public static void initFiles()
	{
		fDir = new java.io.File(rootDir,"dir.xml");
		fComplex = new java.io.File(rootDir,"complex.xml");
	}
    
    @Test
    public void testDir() throws FileNotFoundException
    {
    	Dir test = createDir(false,false);
    	Dir ref = JaxbUtil.loadJAXB(fDir.getAbsolutePath(), Dir.class);
    	assertJaxbEquals(ref, test);
    }
    
    @Test
    public void testComplex() throws FileNotFoundException
    {
    	Dir test = createDir(true,true);
    	Dir ref = (Dir)JaxbUtil.loadJAXB(fComplex.getAbsolutePath(), Dir.class);
    	assertJaxbEquals(ref, test);
    }
    
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Dir xml = createDir(false,false);
    	JaxbUtil.debug2(this.getClass(),xml, getNsPrefixMapper());
    	JaxbUtil.save(fDir, xml, getNsPrefixMapper(), true);
    	
    	Dir xmlComplex = createDir(true,true);
    	JaxbUtil.debug2(this.getClass(),xmlComplex, getNsPrefixMapper());
    	JaxbUtil.save(fComplex, xmlComplex, getNsPrefixMapper(), true);
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
    	
    	if(withFiles){xml.getFile().addAll(TestFile.createFiles());}
    	if(withDirs){xml.getDir().addAll(TestDir.createDirs(true,false));}
    	
    	xml.getPolicy().add(TestPolicy.create(false));
    	
    	return xml;
    }
    
    public static List<Dir> createDirs(boolean withFiles, boolean withDirs)
    {   	
    	List<Dir> list = new ArrayList<Dir>();
    	list.add(createDir(withFiles,withDirs));
    	list.add(createDir(withFiles,withDirs));
    	return list;
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