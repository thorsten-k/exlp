package net.sf.exlp.monitor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.exlp.monitor.model.MonitoringTestSeries;
import net.sf.exlp.test.ExlpMonitorTestBootstrap;

import org.junit.Test;

public class TestHibernate
{

	@Test public void dummy(){}
 
     
    public static void main(String[] args)
    {
    	ExlpMonitorTestBootstrap.init();
    	
       
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("exlp");
        EntityManager em = emf.createEntityManager();
             
        // Creating Contact entity that will be save to the sqlite database
        MonitoringTestSeries myContact = new MonitoringTestSeries(3, "My Name", "my_email@email.com");
        MonitoringTestSeries yourContact = new MonitoringTestSeries(24, "Your Name", "your_email@email.com");
           
        em.getTransaction().begin();
        em.persist(myContact);
        em.getTransaction().commit();
        
        UtilsFacadeBean ufb = new UtilsFacadeBean(em);        
        List<MonitoringTestSeries> contactList = ufb.all(MonitoringTestSeries.class);

        for (MonitoringTestSeries contact : contactList) {
            System.out.println("Id: " + contact.getId() + " | Name:"  + contact.getName() + " | Email:" + contact.getEmail());
        }

    }
}
