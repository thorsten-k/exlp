package org.exlp.util.xml;

import org.exlp.model.xml.io.Dir;
import org.exlp.model.xml.io.TestDir;
import org.exlp.test.ExlpBootstrap;
import org.exlp.util.jx.ExlpNsPrefixMapper;

import net.sf.exlp.util.xml.JaxbUtil;

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
