package net.sf.exlp.addon.monitoring.controller;

import net.sf.exlp.addon.monitoring.dns.DnsStressTest;
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