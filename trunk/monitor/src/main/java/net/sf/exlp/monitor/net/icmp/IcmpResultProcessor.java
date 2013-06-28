package net.sf.exlp.monitor.net.icmp;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcmpResultProcessor implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(IcmpResultProcessor.class);

	private EntityManager em;
	private CompletionService<IcmpResult> csIcmp;
	
    public IcmpResultProcessor(EntityManager em, CompletionService<IcmpResult> csIcmp)
    {
  	    this.em=em;
  	    this.csIcmp=csIcmp;
    }

	@Override
	public void run()
	{
		while(true)
        {
            try
            {
                Future<IcmpResult> future = csIcmp.take();
                IcmpResult result = future.get();
                
                em.getTransaction().begin();
                em.persist(result);
                em.getTransaction().commit();
            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
        }
	}
}