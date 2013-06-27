package net.sf.exlp.addon.monitoring.dns;

import java.net.UnknownHostException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Lookup;

public class DnsStressTest
{
	final static Logger logger = LoggerFactory.getLogger(CliDnsStressTest.class);
	
	public DnsStressTest() throws UnknownHostException
	{
		ExecutorService taskExecutor = Executors.newFixedThreadPool(3);
        CompletionService<DnsResult> taskCompletionService = new ExecutorCompletionService<DnsResult>(taskExecutor);

        int submittedTasks = 100;
        for(int i=0;i< submittedTasks;i++)
        {
        	taskCompletionService.submit(new DnsTask("192.168.1.11","test"+i+".google.com"));
        }
       
        for(int tasksHandled=0;tasksHandled<submittedTasks;tasksHandled++)
        {
            try
            {
                System.out.println(tasksHandled+" trying to take from Completion service");

                Future<DnsResult> result = taskCompletionService.take();
                // above call blocks till atleast one task is completed and results availble for it
                // but we dont have to worry which one

                // process the result here by doing result.get()
                DnsResult l = result.get();
                
        	    if(l.getResult()==Lookup.HOST_NOT_FOUND){logger.info("HOST_NOT_FOUND");}
        	    else if(l.getResult()==Lookup.SUCCESSFUL){logger.info("SUCCESSFUL");}
        	    else if(l.getResult()==Lookup.TRY_AGAIN){logger.info("TRY_AGAIN");}
        	    else if(l.getResult()==Lookup.TYPE_NOT_FOUND){logger.info("TYPE_NOT_FOUND");}
        	    else if(l.getResult()==Lookup.UNRECOVERABLE){logger.info("UNRECOVERABLE");}
        	    
            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
        }
	}
}