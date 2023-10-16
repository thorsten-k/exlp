package org.exlp.util.xml;

import org.exlp.model.xml.io.Dir;
import org.exlp.model.xml.io.TestDir;
import org.exlp.test.ExlpBootstrap;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

public class TestJaxbUtil
{
	public void debug()
	{
		Dir xml = TestDir.createDir(true, true);
		
		JaxbUtil.debug(xml);
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());
		JaxbUtil.debug(xml);
	}
	
	
	public static void main(String[] args)
	{
		ExlpBootstrap.init();
		
		TestJaxbUtil test = new TestJaxbUtil();
		test.debug();
	}
}
