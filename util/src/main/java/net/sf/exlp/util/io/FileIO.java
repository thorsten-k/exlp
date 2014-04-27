package net.sf.exlp.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
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
	
	public static void writeFileIfDiffers(byte[] bytes, File fTarget)
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
				String hashNew = HashUtil.hash(bytes);
				logger.debug("hashExisting "+hashExisting);
				logger.debug("hashNew      "+hashNew);
				if(!hashExisting.equals(hashNew)){doCopy=true;}
				logger.debug("Hash evaluated: COPY:"+doCopy);
			}
			catch (IOException e) {e.printStackTrace();}
		}
		
		if(doCopy)
		{
			try 
			{
				OutputStream fos = new FileOutputStream(fTarget);
				IOUtils.write(bytes, fos);
				fos.close();
			}
			catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
		}
	}
}