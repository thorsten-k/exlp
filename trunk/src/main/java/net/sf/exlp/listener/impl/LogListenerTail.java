package net.sf.exlp.listener.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.exlp.listener.AbstractLogListener;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.parser.LogParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogListenerTail extends AbstractLogListener implements LogListener
{
	static Log logger = LogFactory.getLog(LogListenerTail.class);
	
	private RandomAccessFile raf;
	
	public LogListenerTail(String fileName,LogParser lp){this(new File(fileName),lp);}
	public LogListenerTail(File f,LogParser lp)
	{
		super(lp);
		if(!f.exists())
		{
			logger.fatal("File "+f.getAbsolutePath()+" does not exist!");
			System.exit(-1);
		}
		try 
		{
			raf = new RandomAccessFile(f,"r");
		}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	public void processSingle(){processSingle("0");}
	
	public void processSingle(String position)
	{
		long previousPointer=0;
		try
		{
			raf.seek(new Long(position));
			while(true)
			{
				lp.parseLine(raf.readLine());
				previousPointer = raf.getFilePointer();
				while(previousPointer==raf.length())
				{
					try {Thread.sleep(1000);}
					catch (InterruptedException e) {logger.error(e);}
				}
			}
		}
		catch (IOException e) {logger.error(e);}
	}
}