package net.sf.exlp.addon.monitoring.dns;

import java.net.UnknownHostException;
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
	
	public DnsTask(String dnsServer, String host)
	{
		this.host=host;
		try
		{
			resolver = new SimpleResolver("192.168.1.11");
		}
		catch (UnknownHostException e) {e.printStackTrace();}
	}

	@Override
	public DnsResult call() throws Exception
	{
		Lookup lookup = new Lookup(host, Type.A);
	    lookup.setResolver(resolver);
	    lookup.run();
	    int result = lookup.getResult();
		return new DnsResult(result);
	}
}