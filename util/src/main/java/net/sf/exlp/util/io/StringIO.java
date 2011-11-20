package net.sf.exlp.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringIO
{
	static Log logger = LogFactory.getLog(StringIO.class);
	
	public static synchronized void writeTxt(String dirName, String fileName, String content){writeTxt(new File(dirName), fileName, content);}
	public static synchronized void writeTxt(File fDir, String fileName, String content){writeTxt(new File(fDir,fileName), content);}
	public static synchronized void writeTxt(File f, String content)
	{
		logger.trace("Writing Txt to "+f.getAbsolutePath());
		try
		{
			OutputStream os = new FileOutputStream(f);
			IOUtils.write(content, os, "UTF-8");
			os.flush();os.close();
		}
		catch (IOException e) {logger.error(e);}
	}
	
	public static synchronized String loadTxt(File fDir, String fileName){return loadTxt(new File(fDir,fileName));}
	public static synchronized String loadTxt(String fileName){return loadTxt(new File(fileName));}
	public static synchronized String loadTxt(File f){return loadTxt(f,true);}
	public static synchronized String loadTxt(File f,boolean ls)
	{
		logger.trace("Reading Txt from "+f.getAbsolutePath());
		try
		{
			InputStream is = new FileInputStream(f);
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String theString = writer.toString();
			return theString;
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}