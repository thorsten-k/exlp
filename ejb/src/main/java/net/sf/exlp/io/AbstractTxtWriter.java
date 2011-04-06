package net.sf.exlp.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractTxtWriter
{
	static Log logger = LogFactory.getLog(AbstractTxtWriter.class);
	
	protected String fs;
	protected String ls;
	
	protected ArrayList<String> txt;
	protected String dirName,fileName;
	protected DecimalFormat df;
	
	private String encoding;
	
	public AbstractTxtWriter(String dirName){this(dirName,"UTF-8",SystemUtils.LINE_SEPARATOR);}
	public AbstractTxtWriter(String dirName, String encoding, String ls)
	{
		this.dirName=dirName;
		this.encoding=encoding;
		this.ls=ls;
		fs = SystemUtils.FILE_SEPARATOR;
		txt = new ArrayList<String>();
		df = new DecimalFormat();
	}
	
	public void setDecimalFormat(String s)
	{
		logger.debug("New DecimalFormat: "+s);
		df = new DecimalFormat(s);
	}
	
	public void debug()
	{
		logger.debug("Verzeichnis="+dirName+"\tDatei="+fileName);
		for(String s : txt)
		{
			logger.debug(s);
		}
	}
	
	public void writeFile()
	{
		File f = new File(dirName+System.getProperty("file.separator")+fileName);
		if(f.exists()){f.delete();}
		logger.info("Schreibe in Datei: "+f.getAbsolutePath());
		try
		{
			f.createNewFile();
			OutputStream os = new FileOutputStream(f);
			OutputStreamWriter osw = new  OutputStreamWriter(os, encoding); 

			BufferedWriter bw = new BufferedWriter(osw);
			for(String s : txt){bw.write(s+ls);}
			bw.close();osw.close();os.close();
		}
		catch (IOException e){logger.error(e);}  
	}
	
	public void writeStream(OutputStream os)
	{
		try
		{
			OutputStreamWriter osw = new  OutputStreamWriter(os, encoding); 

			BufferedWriter bw = new BufferedWriter(osw);
			for(String s : txt){bw.write(s+ls);}
			bw.close();osw.close();
		}
		catch (IOException e){logger.error(e);}  
	}
	
	protected String spaces(int indent)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<indent;i++)
		{
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public void tex()
	{
		ArrayList<String> tmp = new ArrayList<String>();
		for(String s : txt)
		{
			s=s.replaceAll("#", "\\\\#");
			s=s.replaceAll("∞","\\$ \\\\inf \\$");
			tmp.add(s);
		}
		txt=tmp;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String s = "∞";
		logger.debug(s);
		
		s=s.replaceAll("∞","\\$ \\\\inf \\$");
		logger.debug(s);
	}
}
