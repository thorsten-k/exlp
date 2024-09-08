package org.exlp.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExlpXmlTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpXmlTest.class);
	
	public AbstractExlpXmlTest(Class<T> cXml, Path pSuffix)
	{
		super(cXml,Paths.get("exlp","system","io","jaxb"),pSuffix);
	}
	

    public static void initLogger()
	{
		LoggerBootstrap.instance().path("exlp/system/io/log").init();
    }
	

	public static void initPrefixMapper()
	{
//		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
	}
	
	protected static Collection<Object[]> initFileNames(String srcDir, String fileSuffix)
	{
		Collection<Object[]> c = new ArrayList<Object[]>();
		File dirSrc = new File(srcDir);
		for(File f : dirSrc.listFiles())
		{
			if(f.getName().endsWith(fileSuffix))
			{
				Object[] o = new Object[] {f};
				c.add(o);
			}
		}
		return c;
	}
}