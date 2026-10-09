package org.exlp.util.jx;

import org.exlp.model.xml.io.Dir;
import org.exlp.model.xml.io.File;
import org.exlp.test.ExlpBootstrap;

public class TestJaxbUtil
{
	public void debug()
	{
		Dir xml = new Dir();
		xml.setCode("test");
		xml.getFile().add(new File());
		
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
