package org.exlp.util.io;

import java.io.FileNotFoundException;

import org.exlp.test.AbstractExlpTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

public class TestMultiResourceLoader extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMultiResourceLoader.class);
    
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