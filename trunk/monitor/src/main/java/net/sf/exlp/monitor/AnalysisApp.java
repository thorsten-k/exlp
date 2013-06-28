package net.sf.exlp.monitor;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.interfaces.UtilsFacade;
import net.sf.exlp.bootstrap.ExlpMonitorBootstrap;
import net.sf.exlp.monitor.controller.MonitoringTask;
import net.sf.exlp.monitor.net.dns.DnsResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisApp
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTask.class);
	
	public AnalysisApp()
	{		
		EntityManagerFactory emf = ExlpMonitorBootstrap.buildEmf(false);
        EntityManager em = emf.createEntityManager();
	
        UtilsFacade ufb = new UtilsFacadeBean(em);
        List<DnsResult> list = ufb.all(DnsResult.class);
        logger.debug("Size: "+list.size());
        
        for(DnsResult item : list)
        {
        	logger.debug(item.toString());
        }
        
        Date from = list.get(5+1).getRecord();
        Date to = list.get(16+1).getRecord();
        logger.debug("From "+from);
        logger.debug("To "+to);
 
        
        for(DnsResult item : ufb.inInterval(DnsResult.class, from, to))
        {
        	logger.debug(item.toString());
        }
	}
}