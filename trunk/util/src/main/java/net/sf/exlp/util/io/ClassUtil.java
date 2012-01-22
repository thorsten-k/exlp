package net.sf.exlp.util.io;

import java.io.File;

import net.sf.exlp.util.io.RelativePathFactory.PathSeparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil
{
	final static Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	
	public static Class<?> forFile(File fBase, File fClass) throws ClassNotFoundException
    {
		RelativePathFactory rpf = new RelativePathFactory(fBase,PathSeparator.CURRENT);
		String sClass = rpf.relativate(fClass);
		sClass = sClass.substring(0, sClass.indexOf(".java"));
		sClass=sClass.replaceAll("/", ".");

		Class<?> c = Class.forName(sClass);
		return c;
    }
}
