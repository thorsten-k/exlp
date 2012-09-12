package net.sf.exlp.util.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessClock
{
	final static Logger logger = LoggerFactory.getLogger(ProcessClock.class);
	
	private List<ProcessClockStep> steps;
	private Long start;
	
	
	public ProcessClock()
	{
		steps = new ArrayList<ProcessClockStep>();
	}
	
	public void add(String event)
	{
		if(start==null)
		{
			start = System.currentTimeMillis();
			steps.add(new ProcessClockStep(event,0));
		}
		else
		{
			steps.add(new ProcessClockStep(event,(System.currentTimeMillis()-start)));
		}
	}
	
	public void debug(Logger myLogger)
	{
		for(ProcessClockStep s : steps){myLogger.debug(s.toString());}
	}
	
	public void info(Logger myLogger)
	{
		for(ProcessClockStep s : steps){myLogger.info(s.toString());}
	}
	
	public void warn(Logger myLogger)
	{
		for(ProcessClockStep s : steps){myLogger.warn(s.toString());}
	}
	
	public void error(Logger myLogger)
	{
		for(ProcessClockStep s : steps){myLogger.error(s.toString());}
	}

	private class ProcessClockStep
	{
		private String event;
		private long time;
		
		public ProcessClockStep(String event, long time)
		{
			this.event=event;
			this.time=time;
		}
		
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			sb.append(time);
			sb.append("\t").append(event);
			return sb.toString();
		}
	}
	
}