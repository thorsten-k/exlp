package net.sf.exlp.addon.monitoring.icmp;

import net.sf.exlp.addon.monitoring.dns.DnsStressTest;
import net.sf.exlp.test.ExlpAddonTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliIcmpTask
{
	final static Logger logger = LoggerFactory.getLogger(DnsStressTest.class);
	
	public static void main(String args[]) throws Exception
	{
		ExlpAddonTestBootstrap.init();
		
		IcmpTask task = new IcmpTask("web.de");
		task.call();
		
	}
}