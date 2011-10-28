package net.sf.exlp.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
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
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(content);
			bw.close();
		}
		catch (IOException e) {logger.error(e);}
	}
	
	public static synchronized String loadTxt(File fDir, String fileName){return loadTxt(new File(fDir,fileName));}
	public static synchronized String loadTxt(String fileName){return loadTxt(new File(fileName));}
	public static synchronized String loadTxt(File f){return loadTxt(f,true);}
	public static synchronized String loadTxt(File f,boolean ls)
	{
		logger.trace("Reading Txt from "+f.getAbsolutePath());
		StringBuffer sb = new StringBuffer();
		try
		{
			BufferedReader bw = new BufferedReader(new FileReader(f));
			while(bw.ready())
			{
				sb.append(bw.readLine());
				if(ls){sb.append(SystemUtils.LINE_SEPARATOR);}
			}
			bw.close();
		}
		catch (IOException e) {logger.error(e);}
		return sb.toString();
	}
}