package net.sf.exlp.monitor;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.exlp.monitor.net.controller.MonitoringTask;
import net.sf.exlp.monitor.net.controller.MonitoringTaskFactory;
import net.sf.exlp.monitor.net.dns.DnsResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorApp
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTask.class);
	
	public MonitorApp()
	{
		ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
        CompletionService<DnsResult> csDns = new ExecutorCompletionService<DnsResult>(taskExecutor);
        
        logger.debug("Creating "+MonitoringTaskFactory.class.getSimpleName());
        MonitoringTaskFactory mtf = new MonitoringTaskFactory();
        mtf.setCsDns(csDns);
        logger.debug("Created "+MonitoringTaskFactory.class.getSimpleName());
        
        Thread tMtf = new Thread(mtf);
        logger.info("Starting  "+MonitoringTaskFactory.class.getSimpleName());
        tMtf.start();
        
        while(true)
        {
            try
            {
                Future<DnsResult> result = csDns.take();
                
                DnsResult l = result.get();
/*                
        	    if(l.getResult()==Lookup.HOST_NOT_FOUND){logger.info("HOST_NOT_FOUND");}
        	    else if(l.getResult()==Lookup.SUCCESSFUL){logger.info("SUCCESSFUL");}
        	    else if(l.getResult()==Lookup.TRY_AGAIN){logger.info("TRY_AGAIN");}
        	    else if(l.getResult()==Lookup.TYPE_NOT_FOUND){logger.info("TYPE_NOT_FOUND");}
        	    else if(l.getResult()==Lookup.UNRECOVERABLE){logger.info("UNRECOVERABLE");}
*/            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
        }
	}
}