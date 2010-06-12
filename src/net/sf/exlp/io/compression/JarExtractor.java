package net.sf.exlp.io.compression;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JarExtractor
{
	static Log logger = LogFactory.getLog(JarExtractor.class);
	
	public JarExtractor()
	{
		
	}
	
	public static synchronized void extract(String jarName, String from, String to)
	{
		try
		{
			JarFile jar = new JarFile(jarName);
			JarEntry entry = jar.getJarEntry(from);
			InputStream is = jar.getInputStream(entry);
			FileOutputStream fos = new FileOutputStream(to);
			while (is.available() > 0)
			{ 
				fos.write(is.read());
			}
			fos.close();
			is.close();
		}
		catch (IOException e) {logger.error(e);}
	}
}
