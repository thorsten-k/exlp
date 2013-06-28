package net.sf.exlp.monitor.controller;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.CompletionService;

import net.sf.exlp.monitor.net.dns.DnsResult;
import net.sf.exlp.monitor.net.icmp.IcmpResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringTaskFactory implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTaskFactory.class);

	private CompletionService<DnsResult> csDns;
	private CompletionService<IcmpResult> csIcmp;

	private boolean active;
	
    public MonitoringTaskFactory()
    {
  	    active=true;
    }

	@Override
	public void run()
	{
        MonitoringTask monitoringTask  = new MonitoringTask(1);
    	monitoringTask.setCsDns(csDns);
    	monitoringTask.setCsIcmp(csIcmp);

 	    Timer timer = new Timer();
 	    logger.info("Starting timer");
 	    timer.scheduleAtFixedRate(monitoringTask, new Date(), 1000);
 	    logger.info("Time scheduled");
	}
	
	public void shutdown()
	{
		
	}
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
	public void setCsIcmp(CompletionService<IcmpResult> csIcmp) {this.csIcmp = csIcmp;}
}