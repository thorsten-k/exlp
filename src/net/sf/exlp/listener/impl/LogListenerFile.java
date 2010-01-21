package net.sf.exlp.listener.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import net.sf.exlp.listener.AbstractLogListener;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.parser.LogParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LogListenerFile extends AbstractLogListener implements LogListener
{
	static Log logger = LogFactory.getLog(LogListenerFile.class);
	
	Pattern gzipPattern;
	BufferedReader myBufferedReader;
	String charSet;
	File f;
	
	public LogListenerFile(String fileName,LogParser lp){this(new File(fileName),null,lp);}
	public LogListenerFile(String fileName,String charSet,LogParser lp){this(new File(fileName),charSet,lp);}
	public LogListenerFile(File f,LogParser lp){this(f,null,lp);}
	public LogListenerFile(File f,String charSet,LogParser lp)
	{
		super(lp);
		this.charSet=charSet;
		this.f=f;
		if(!f.exists())
		{
			logger.fatal("File "+f.getAbsolutePath()+" does not exist!");
			System.exit(-1);
		}
		String s="[a-z0-9A-Z\\.]*\\.(gz)";
		gzipPattern = Pattern.compile(s);
		Matcher gzipMatcher=gzipPattern.matcher(f.getName());
		if(gzipMatcher.matches())
		{
			logger.debug("File "+f.toString()+" ist ein GZ");
			openGzFile(f);
		}
		else
		{
			logger.debug("File "+f.toString()+" wird gelesen");
			openFile(f);
		}
	}
	
	private void openGzFile(File myFile)
	{
		try
		{
			FileInputStream fis = new FileInputStream (myFile);
			GZIPInputStream gzis = new GZIPInputStream(fis);
			InputStreamReader isr;
			if(charSet==null) {isr = new InputStreamReader(gzis);}
			else {isr = new InputStreamReader(gzis,charSet);}
			myBufferedReader = new BufferedReader(isr);
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	private void openFile(File myFile)
	{
		String logCset = "";
		try
		{
			FileInputStream fis = new FileInputStream (myFile);
			InputStreamReader isr;
			if(charSet==null) {isr = new InputStreamReader(fis);}
			else
			{
				logCset = " with Charset "+charSet;
				isr = new InputStreamReader(fis,charSet);
			}
			myBufferedReader = new BufferedReader(isr);
		}
		catch (FileNotFoundException e)	{e.printStackTrace();}
		catch (UnsupportedEncodingException e) {e.printStackTrace();}
		logger.trace("File "+myFile.getName()+" is open."+logCset);
	}
	
	public void processSingle()
	{
		try
		{
			String Zeile; 
			while(null != (Zeile = myBufferedReader.readLine()))
			{
				lp.parseLine(Zeile,f.getAbsolutePath());
			}
		    myBufferedReader.close();
		    debug();
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void processMulti(String na)
	{
		try
		{
			ArrayList<String> item = new ArrayList<String>();
			String Zeile; 
			while(null != (Zeile = myBufferedReader.readLine()))
			{
				item.add(Zeile);
			}
		    myBufferedReader.close();
		    lp.addMetaInfo(metaInfo);
		    lp.parseItem(item);
		    debug();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}