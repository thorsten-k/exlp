package net.sf.exlp.monitor.net.dns;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.Type;

public class DnsTask implements Callable<DnsResult>
{
	final static Logger logger = LoggerFactory.getLogger(DnsTask.class);
	
	private Resolver resolver;
	private String host;
	private Date record;
	
	public DnsTask(String dnsServer, String host)
	{
		this.host=host;
		record = new Date();
		try
		{
			resolver = new SimpleResolver(dnsServer);
		}
		catch (UnknownHostException e) {e.printStackTrace();}
	}

	@Override
	public DnsResult call() throws Exception
	{
		logger.debug("DNS request for "+host);
		DnsResult dnsResult = new DnsResult();
		
		Lookup lookup = new Lookup(host, Type.A);
	    lookup.setResolver(resolver);
	    
	    long startTime = System.currentTimeMillis();
	    lookup.run();
	    dnsResult.setDuration(System.currentTimeMillis()-startTime);
	    int result = lookup.getResult();
	    dnsResult.setCode(DnsResult.Code.values()[lookup.getResult()]);
	    dnsResult.setRecord(record);
	    
	    if(result==Lookup.HOST_NOT_FOUND){logger.info("HOST_NOT_FOUND");}
	    else if(result==Lookup.SUCCESSFUL){logger.info("SUCCESSFUL");}
	    else if(result==Lookup.TRY_AGAIN){logger.info("TRY_AGAIN");}
	    else if(result==Lookup.TYPE_NOT_FOUND){logger.info("TYPE_NOT_FOUND");}
	    else if(result==Lookup.UNRECOVERABLE){logger.info("UNRECOVERABLE");}
	    
		return dnsResult;
	}
}