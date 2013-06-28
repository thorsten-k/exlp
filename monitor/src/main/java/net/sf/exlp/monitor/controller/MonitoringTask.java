package net.sf.exlp.monitor.controller;

import java.util.TimerTask;
import java.util.concurrent.CompletionService;

import net.sf.exlp.monitor.net.dns.DnsResult;
import net.sf.exlp.monitor.net.dns.DnsTask;
import net.sf.exlp.monitor.net.icmp.IcmpResult;
import net.sf.exlp.monitor.net.icmp.IcmpTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringTask extends TimerTask 
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTask.class);
	
	private int counter;
	private CompletionService<DnsResult> csDns;
	private CompletionService<IcmpResult> csIcmp;

	public MonitoringTask(int timeInterval)
	{
		counter=1;
	}
	
	public void run()
	{
		if(csDns!=null){buildDnsTask();}
		if(csIcmp!=null){buildIcmpTask();}
		counter++;
	}
	
	private void buildDnsTask()
	{
		DnsTask task = new DnsTask("8.8.8.8","test"+counter+".google.com");
		
		csDns.submit(task);
	}
	
	private void buildIcmpTask()
	{
		IcmpTask task = new IcmpTask("www.google.com");
		csIcmp.submit(task);
	}
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
	public void setCsIcmp(CompletionService<IcmpResult> csIcmp) {this.csIcmp = csIcmp;}
}
