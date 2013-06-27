package net.sf.exlp.monitor.controller;

import net.sf.exlp.monitor.net.controller.MonitoringTaskFactory;
import net.sf.exlp.monitor.net.dns.DnsStressTest;
import net.sf.exlp.test.ExlpAddonTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringTaskFactory
{
	final static Logger logger = LoggerFactory.getLogger(DnsStressTest.class);
	
	public static void main (String[] args)
	{
		ExlpAddonTestBootstrap.init();
		
	    new MonitoringTaskFactory();

	  }
}