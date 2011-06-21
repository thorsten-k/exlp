package net.sf.exlp.util.io.compression;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JarStream
{
	static Log logger = LogFactory.getLog(JarStream.class);
	
	private ByteArrayOutputStream resultOs;
	private JarOutputStream zipOs;
	
	public JarStream()
	{
		resultOs = new ByteArrayOutputStream();
		try {zipOs = new JarOutputStream(resultOs);}
		catch (IOException e) {logger.error(e);}
	}
	
	public void add(String name, byte[] data)
	{
		try
		{
			zipOs.putNextEntry(new JarEntry(name));
			zipOs.write(data);
		}
		catch (IOException e) {logger.error(e);}
	}
	
	public OutputStream getJarStream()
	{
		try {zipOs.close();}
		catch (IOException e) {logger.error(e);}
		return resultOs;
	}
	
	public static void writeFile(String fileName,OutputStream os)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			ByteArrayOutputStream baos = (ByteArrayOutputStream)os;
			fos.write(baos.toByteArray());
			fos.close();
		}
		catch (FileNotFoundException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
	}
}