package net.sf.exlp.util.xml;

import net.sf.exlp.test.ExlpTstBootstrap;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.TestDir;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

public class TstJaxbUtil
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
		ExlpTstBootstrap.init();
		
		TstJaxbUtil test = new TstJaxbUtil();
		test.debug();
	}
}
