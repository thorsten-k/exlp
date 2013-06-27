package net.sf.exlp.monitor.controller;

import net.sf.exlp.monitor.net.controller.MonitoringTaskFactory;
import net.sf.exlp.test.ExlpMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringTaskFactory
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringTaskFactory.class);
	
	public static void main (String[] args)
	{
		ExlpMonitorTestBootstrap.init();
		
	    new MonitoringTaskFactory();

	  }
}