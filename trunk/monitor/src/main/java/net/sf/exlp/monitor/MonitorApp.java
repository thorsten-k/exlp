package net.sf.exlp.monitor;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.exlp.bootstrap.ExlpMonitorBootstrap;
import net.sf.exlp.monitor.controller.MonitoringTask;
import net.sf.exlp.monitor.controller.MonitoringTaskFactory;
import net.sf.exlp.monitor.net.dns.DnsResult;
import net.sf.exlp.monitor.net.dns.DnsResultProcessor;
import net.sf.exlp.monitor.net.icmp.IcmpResult;
import net.sf.exlp.monitor.net.icmp.IcmpResultProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorApp
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTask.class);
	
	public MonitorApp()
	{
    	EntityManagerFactory emf = ExlpMonitorBootstrap.buildEmf(true);
        EntityManager em = emf.createEntityManager();
		
		ExecutorService taskExecutor = Executors.newFixedThreadPool(100);
        CompletionService<DnsResult> csDns = new ExecutorCompletionService<DnsResult>(taskExecutor);
        CompletionService<IcmpResult> csIcmp = new ExecutorCompletionService<IcmpResult>(taskExecutor);
        
        logger.debug("Creating "+MonitoringTaskFactory.class.getSimpleName());
        MonitoringTaskFactory mtf = new MonitoringTaskFactory();
        mtf.setCsDns(csDns);
        mtf.setCsIcmp(csIcmp);
        
        Thread tMonitoringTaskFactory = new Thread(mtf);
        tMonitoringTaskFactory.start();
        
        Thread dnsResultProcessor = new Thread(new DnsResultProcessor(emf.createEntityManager(),csDns));
        dnsResultProcessor.start();
        
        Thread icmpResultProcessor = new Thread(new IcmpResultProcessor(emf.createEntityManager(),csIcmp));
        icmpResultProcessor.start();
        
        UtilsFacadeBean ufb = new UtilsFacadeBean(em);
        for(int i=0;i<30;i++)
        {
            logger.debug("DNS:"+ufb.all(DnsResult.class).size()+" ICMP:"+ufb.all(IcmpResult.class).size());
            try {Thread.sleep(1000);}
            catch (InterruptedException e) {e.printStackTrace();}
        }
        logger.info("Stopping Threads ...");
	}
}