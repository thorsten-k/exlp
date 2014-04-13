package net.sf.exlp.util.io;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIO
{
	final static Logger logger = LoggerFactory.getLogger(FileIO.class);
	
	@Deprecated
	public static String getHash(File f) throws IOException
    {
    	return HashUtil.hash(f);
    }
}