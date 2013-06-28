package net.sf.exlp.monitor.net.dns;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnsResultProcessor implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(DnsResultProcessor.class);

	private EntityManager em;
	private CompletionService<DnsResult> csDns;
	
    public DnsResultProcessor(EntityManager em, CompletionService<DnsResult> csDns)
    {
  	    this.em=em;
  	    this.csDns=csDns;
    }

	@Override
	public void run()
	{
		while(true)
        {
            try
            {
                Future<DnsResult> future = csDns.take();
                DnsResult result = future.get();
                
                em.getTransaction().begin();
                em.persist(result);
                em.getTransaction().commit();
            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
        }
	}
}