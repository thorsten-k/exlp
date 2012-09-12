package net.sf.exlp.util.io.txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTxtWriter
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTxtWriter.class);
	
	protected String lineSeparator;	
	private String encoding;
	
	protected ArrayList<String> txt;
	protected String dirName,fileName;
	
	protected DecimalFormat df;
	
	public AbstractTxtWriter()
	{
		encoding = "UTF-8";
		lineSeparator = SystemUtils.LINE_SEPARATOR;
		
		txt = new ArrayList<String>();
		df = new DecimalFormat();
	}
	
	public void clear()
	{
		txt.clear();
	}
	
	public void setDecimalFormat(String s)
	{
		logger.debug("New DecimalFormat: "+s);
		df = new DecimalFormat(s);
	}
	
	public void debug()
	{
		logger.debug("Debugging TXT content");
		for(String s : txt)
		{
			logger.debug(s);
		}
	}
	
	public void write() throws NoSuchElementException
	{
		if(dirName == null){throw new NoSuchElementException("Field dirName not set!");}
		else if(fileName == null){throw new NoSuchElementException("Field fileName not set!");}
		else
		{
			File f = new File(dirName,fileName);
			writeFile(f);
		}
	}
	
	public void writeFile(String dir, String file){writeFile(new File(dir,file));}
	public void writeFile(File f)
	{
		if(f.exists()){f.delete();}
		logger.debug("Writing file: "+f.getAbsolutePath());
		try
		{
			f.createNewFile();
			OutputStream os = new FileOutputStream(f);
			writeStream(os);
			os.close();
		}
		catch (IOException e){logger.error("IOException",e);}  
	}
	
	public void writeStream(OutputStream os)
	{
		try
		{
			OutputStreamWriter osw = new  OutputStreamWriter(os, encoding); 

			BufferedWriter bw = new BufferedWriter(osw);
			for(String s : txt){bw.write(s+lineSeparator);}
			bw.close();osw.close();
		}
		catch (IOException e){logger.error("IOException",e);}  
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
	
	public String getEncoding() {return encoding;}
	public void setEncoding(String encoding) {this.encoding = encoding;}
	
	public String getLineSeparator() {return lineSeparator;}
	public void setLineSeparator(String lineSeparator) {this.lineSeparator = lineSeparator;}
	
	public String getDirName() {return dirName;}
	public void setDirName(String dirName) {this.dirName = dirName;}

	public String getFileName() {return fileName;}
	public void setFileName(String fileName) {this.fileName = fileName;}
}