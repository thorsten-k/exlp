package net.sf.exlp.monitor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.exlp.monitor.model.MonitoringTestSeries;
import net.sf.exlp.monitor.net.controller.MonitoringTask;
import net.sf.exlp.test.ExlpMonitorTestBootstrap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHibernate
{
	final static Logger logger = LoggerFactory.getLogger(TestHibernate.class);
	
	@Test public void dummy(){}
 
     
    public static void main(String[] args)
    {
    	ExlpMonitorTestBootstrap.init();
    	
       
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("exlp");
        EntityManager em = emf.createEntityManager();
             
        // Creating Contact entity that will be save to the sqlite database
        MonitoringTestSeries myContact = new MonitoringTestSeries("My Name");
           
        em.getTransaction().begin();
        em.persist(myContact);
        em.getTransaction().commit();
        
        UtilsFacadeBean ufb = new UtilsFacadeBean(em);        
        List<MonitoringTestSeries> contactList = ufb.all(MonitoringTestSeries.class);

        for (MonitoringTestSeries mts : contactList)
        {
            logger.debug(mts.toString());
        }
    }
}
