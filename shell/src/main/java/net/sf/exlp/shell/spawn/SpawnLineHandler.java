package net.sf.exlp.shell.spawn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.interfaces.LogParser;

public class SpawnLineHandler extends Thread
{
	final static Logger logger = LoggerFactory.getLogger(SpawnLineHandler.class);
	
	private static String ls = System.lineSeparator();
	private String prefix;
	private BufferedReader brIn;
	private Writer writer;
	private LogParser lp;
	
	public SpawnLineHandler(String prefix, InputStreamReader isr)
	{
		this.prefix=prefix;
		brIn= new BufferedReader(isr);
	}
	
	public void run()
	{
		logger.debug("Starting: "+toString());
		if(writer==null && lp==null){writer=new OutputStreamWriter(System.out);}
		String line;
		boolean useWriter = writer!=null;
		boolean useParser = lp!=null;
		try
		{
			while ((line = brIn.readLine()) != null)
			{
				logger.trace("*******"+prefix+" "+line);
				if(useWriter)
				{
					writer.write(prefix+line+ls);
					writer.flush();
				}
				if(useParser)
				{
					lp.parseLine(line);
				}
			}
			brIn.close();
			if(lp!=null) {lp.parseLine(Spawn.exitValueIdentifier);}
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void setWriter(Writer writer) {this.writer = writer;}
	public void setLp(LogParser lp) {this.lp = lp;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(SpawnLineHandler.class.getSimpleName());
		sb.append(" ");
		if(prefix.equals("I:")){sb.append(" for std.out");}
		if(prefix.equals("E:")){sb.append(" for std.err");}
		return sb.toString();
	}
}
