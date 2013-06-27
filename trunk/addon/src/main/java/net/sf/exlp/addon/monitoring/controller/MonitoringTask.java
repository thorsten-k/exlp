package net.sf.exlp.addon.monitoring.controller;

import java.util.TimerTask;
import java.util.concurrent.CompletionService;

import net.sf.exlp.addon.monitoring.dns.DnsResult;
import net.sf.exlp.addon.monitoring.dns.DnsTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringTask extends TimerTask 
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTask.class);
	
	private int counter;
	private CompletionService<DnsResult> csDns;

	public MonitoringTask(int timeInterval)
	{
		counter=1;
	}
	
	public void run()
	{
		if(csDns!=null){buildDnsTask();}
	}
	
	private void buildDnsTask()
	{
		csDns.submit(new DnsTask("192.168.1.11","test"+counter+".google.com"));
	}
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
}
