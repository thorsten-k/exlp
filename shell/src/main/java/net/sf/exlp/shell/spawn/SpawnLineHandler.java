package net.sf.exlp.shell.spawn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.sf.exlp.interfaces.LogParser;

import org.apache.commons.lang.SystemUtils;


public class SpawnLineHandler extends Thread
{
	private static String ls = SystemUtils.LINE_SEPARATOR;
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
		if(writer==null && lp==null){writer=new OutputStreamWriter(System.out);}
		String Zeile;
		boolean useWriter = writer!=null;
		boolean useParser = lp!=null;
		try
		{
			while ((Zeile = brIn.readLine()) != null)
			{
				if(useWriter)
				{
					writer.write(prefix+Zeile+ls);
					writer.flush();
				}
				if(useParser) {lp.parseLine(Zeile);}
			}
			brIn.close();
			if(lp!=null) {lp.parseLine(Spawn.exitValueIdentifier);}
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void setWriter(Writer writer) {this.writer = writer;}
	public void setLp(LogParser lp) {this.lp = lp;}
}
