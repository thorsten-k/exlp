package net.sf.exlp.monitor.net.dns;

import net.sf.exlp.monitor.net.dns.DnsStressTest;
import net.sf.exlp.test.ExlpAddonTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliDnsStressTest
{
	final static Logger logger = LoggerFactory.getLogger(DnsStressTest.class);
	
	public static void main(String args[]) throws Exception
	{
		ExlpAddonTestBootstrap.init();
		
		DnsStressTest dnsTest = new DnsStressTest();
		
	}
}