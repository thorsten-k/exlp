package net.sf.exlp.util.io.resourceloader;

import java.io.FileNotFoundException;

import net.sf.exlp.test.AbstractExlpTst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstMultiResourceLoader extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TstMultiResourceLoader.class);
    
	public static void main(String[] args) throws FileNotFoundException
	{
		MultiResourceLoader mrl1 = new MultiResourceLoader();
		mrl1.addPath("dir1","dir2","dir3");
		mrl1.addPath("exlp-util.test");
		mrl1.searchIs("log4j.xml");
		System.out.println(mrl1.getSearchPath());
		
		MultiResourceLoader mrl3 = new MultiResourceLoader();
		mrl3.addPath("dir1","dir2","dir3");
		mrl3.addPath("exlp-util.test");
		System.out.println(mrl3.getLocation("log4j.xml"));
	}
}