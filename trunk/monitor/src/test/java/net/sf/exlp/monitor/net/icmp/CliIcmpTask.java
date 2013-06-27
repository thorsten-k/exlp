package net.sf.exlp.monitor.net.icmp;

import net.sf.exlp.monitor.net.dns.DnsStressTest;
import net.sf.exlp.monitor.net.icmp.IcmpTask;
import net.sf.exlp.test.ExlpMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliIcmpTask
{
	final static Logger logger = LoggerFactory.getLogger(DnsStressTest.class);
	
	public static void main(String args[]) throws Exception
	{
		ExlpMonitorTestBootstrap.init();
		
		IcmpTask task = new IcmpTask("web.de");
		task.call();
		
	}
}