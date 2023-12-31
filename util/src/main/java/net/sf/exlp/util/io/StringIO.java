package net.sf.exlp.util.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.exlp.util.io.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringIO
{
	final static Logger logger = LoggerFactory.getLogger(StringIO.class);
	
	public static void writeTxtIfDiffers(String content, File fTarget)
	{
		boolean doCopy = false;
		if(!fTarget.exists())
		{
			logger.debug(fTarget.getAbsolutePath()+" does not exist, so write");
			doCopy=true;
		}
		else
		{
			try
			{
				String hashExisting = HashUtil.hash(fTarget);
				String hashNew = HashUtil.hash(content);
				logger.debug("hashExisting "+hashExisting);
				logger.debug("hashNew      "+hashNew);
				if(!hashExisting.equals(hashNew)){doCopy=true;}
				logger.debug("Hash evaluated: COPY:"+doCopy);
			}
			catch (IOException e) {e.printStackTrace();}
		}
		
		if(doCopy){writeTxt(content,fTarget);}
		else{logger.debug("Dont copy");}
	}
	
	public static synchronized void writeTxt(String dirName, String fileName, String content){writeTxt(new File(dirName), fileName, content);}
	public static synchronized void writeTxt(File fDir, String fileName, String content){writeTxt(new File(fDir,fileName), content);}
	public static synchronized void writeTxt(File f, String content){writeTxt(content,f);}
	public static synchronized void writeTxt(String content, File f)
	{
		logger.trace("Writing Txt to "+f.getAbsolutePath());
		try
		{
			OutputStream os = new FileOutputStream(f);
			IOUtils.write(content, os, "UTF-8");
			os.flush();os.close();
		}
		catch (IOException e) {logger.error("",e);}
	}
	
	public static synchronized String loadTxt(File fDir, String fileName){return StringUtil.readFile(new File(fDir,fileName));}
	public static synchronized String loadTxt(String fileName){return StringUtil.readFile(new File(fileName));}
	
}