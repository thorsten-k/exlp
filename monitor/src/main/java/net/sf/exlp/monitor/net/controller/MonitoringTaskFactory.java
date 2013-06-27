package net.sf.exlp.monitor.net.controller;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.exlp.monitor.net.dns.DnsResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Lookup;

public class MonitoringTaskFactory implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTaskFactory.class);

	private CompletionService<DnsResult> csDns;
	
    public MonitoringTaskFactory()
    {
  	    
 	    logger.info("Waiting ...");
 	  
    }

	@Override
	public void run()
	{
        MonitoringTask monitoringTask  = new MonitoringTask(1);
    	monitoringTask.setCsDns(csDns);

 	    Timer timer = new Timer();
 	    logger.info("Starting timer");
 	    timer.scheduleAtFixedRate(monitoringTask, new Date(), 1000);
	}
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
}