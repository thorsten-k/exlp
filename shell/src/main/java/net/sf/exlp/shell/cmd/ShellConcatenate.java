package net.sf.exlp.shell.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellConcatenate
{
	final static Logger logger = LoggerFactory.getLogger(ShellConcatenate.class);
	
	public static String concatenate()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("cat");
		for(int i=1;i<10;i++)
		{
			sb.append(" small.z0").append(i);
			
		}
		for(int i=10;i<100;i++)
		{
			sb.append(" small.z").append(i);
			
		}
		for(int i=100;i<=977;i++)
		{
			sb.append(" small.z").append(i);
			
		}
		sb.append(" small.zip > all.zip");
		return sb.toString();
	}

	
	public static void main(String[] args)
	{
		System.out.println(ShellConcatenate.concatenate());
	}
}
