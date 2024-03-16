package org.exlp.controller.handler.io.log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggedExit
{
	final static Logger logger = LoggerFactory.getLogger(LoggedExit.class);
	
	public static void exit(boolean exit)
	{
		if(exit)
		{
			logger.warn("Process will be exited.");
		
			StackTraceElement[] traces = Thread.currentThread().getStackTrace();
	     
			// 2: Caller of the method
			// 1: The current method
			// 0: getStackTrace method itself
	        
			for(int i=2; i<traces.length;i++)
			{
				logger.info(StringUtils.repeat(" ",i-2)+" "+traces[i].getClassName());
			}
			
	        System.exit(0);
	    }
	}
	
	public static void silent(boolean exit)
	{
		if(exit)
		{
	        System.exit(0);
	    }
	}
}