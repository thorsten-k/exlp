package net.sf.exlp.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

public abstract class AbstractTxtWriter
{
	static Logger logger = Logger.getLogger(AbstractTxtWriter.class);
	
	protected static String fs = SystemUtils.FILE_SEPARATOR;
	protected static String ls = SystemUtils.LINE_SEPARATOR;
	
	protected ArrayList<String> txt;
	protected String dirName,fileName;
	protected DecimalFormat df;
	
	public AbstractTxtWriter(String dirName)
	{
		this.dirName=dirName;
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
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for(String s : txt){bw.write(s+ls);}
			bw.close(); 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}  
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
			tmp.add(s);
		}
		txt=tmp;
	}
}
