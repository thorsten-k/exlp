package org.exlp.model.xml.io;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestDir extends AbstractIoXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestDir.class);
	
	private static java.io.File fDir, fComplex;
	
	@BeforeAll
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
    	JaxbUtil.debug(xml);
    	JaxbUtil.save(fDir, xml, true);
    	
    	Dir xmlComplex = createDir(true,true);
    	JaxbUtil.debug(xmlComplex);
    	JaxbUtil.save(fComplex, xmlComplex, true);
    }
    
    public static Dir createDir(boolean withFiles,boolean withDirs)
    {
    	Dir xml = new Dir();
    	xml.setId(1l);
    	xml.setCode("code");
    	xml.setName("test.txt");
    	xml.setAllowCreate(true);
    	xml.setLastModifed(DateUtil.toXmlGc(LocalDateTime.of(2012,1,1,10,10,10)));
    	
    	if(withFiles){xml.getFile().addAll(TestXmlFile.createFiles());}
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
		ExlpBootstrap.init();	
			
		TestDir.initFiles();	
		TestDir test = new TestDir();
		test.save();
    }
}