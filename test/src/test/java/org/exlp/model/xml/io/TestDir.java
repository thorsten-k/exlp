package org.exlp.model.xml.io;

import java.time.LocalDateTime;

import org.exlp.test.ExlpTestBootstrap;
import org.exlp.util.system.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDir extends AbstractIoXmlTest<Dir>
{
	final static Logger logger = LoggerFactory.getLogger(TestDir.class);
	
	public static TestDir instance() {return new TestDir();}
	private TestDir() {super(Dir.class);}
    
    @Override public Dir build(boolean wChildren)
    {
    	Dir xml = new Dir();
    	xml.setId(1l);
    	xml.setCode("code");
    	xml.setName("test.txt");
    	xml.setAllowCreate(true);
    	xml.setLastModifed(DateUtil.toXmlGc(LocalDateTime.of(2012,1,1,10,10,10)));
    	
    	if(wChildren)
    	{
    		xml.getFile().add(TestXmlFile.instance().build(false));
    		xml.getFile().add(TestXmlFile.instance().build(false));
    		xml.getDir().add(TestDir.instance().build(false));
    		xml.getDir().add(TestDir.instance().build(false));
    		xml.getPolicy().add(TestPolicy.instance().build(false));
    	}
    	return xml;
    }
	
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestDir.instance().saveReferenceXml();
    }
}