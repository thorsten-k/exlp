package net.sf.exlp.addon.monitoring;

import net.sf.exlp.test.ExlpAddonTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitorApp
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitorApp.class);
	
	public static void main (String[] args)
	{
		ExlpAddonTestBootstrap.init();
		
	    new MonitorApp();

	  }
}