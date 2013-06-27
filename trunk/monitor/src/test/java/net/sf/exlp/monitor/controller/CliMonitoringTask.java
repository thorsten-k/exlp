package net.sf.exlp.monitor.controller;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.exlp.monitor.net.controller.MonitoringTask;
import net.sf.exlp.test.ExlpMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringTask
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringTask.class);
	
	public static void main (String... arguments )
	{
		ExlpMonitorTestBootstrap.init();
		
	    TimerTask fetchMail  = new MonitoringTask(1);

	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(fetchMail, new Date(), 1000);
	  }
}