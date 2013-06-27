package net.sf.exlp.monitor.net.dns;

import net.sf.exlp.test.ExlpMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliDnsTask
{
	final static Logger logger = LoggerFactory.getLogger(CliDnsTask.class);
	
	public static void main(String args[]) throws Exception
	{
		ExlpMonitorTestBootstrap.init();
		
		DnsTask dnsTask = new DnsTask("192.168.1.11","www.google.com");
		
	}
}