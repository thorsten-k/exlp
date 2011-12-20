package net.sf.exlp.listener.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import net.sf.exlp.listener.AbstractLogListener;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.parser.LogParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogListenerString extends AbstractLogListener implements LogListener
{
	final static Logger logger = LoggerFactory.getLogger(LogListenerString.class);

	private String s;
	
	public LogListenerString(String s,LogParser lp)
	{
		super(lp);
		this.s=s;
	}
	
	@Override
	public void processSingle()
	{
		StringReader sr = new StringReader(s);
		BufferedReader br = new BufferedReader(sr);
		try
		{
			String line; 
			while(null != (line = br.readLine()))
			{
				lp.parseLine(line);
			}
			lp.close();
		    br.close();
		    sr.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}