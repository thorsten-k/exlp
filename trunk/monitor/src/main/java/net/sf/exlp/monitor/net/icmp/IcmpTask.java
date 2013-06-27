package net.sf.exlp.monitor.net.icmp;

import java.net.InetAddress;
import java.util.concurrent.Callable;

import net.sf.exlp.monitor.net.dns.DnsResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcmpTask implements Callable<DnsResult>
{
	final static Logger logger = LoggerFactory.getLogger(IcmpTask.class);
	
	private String host;
	
	public IcmpTask(String host)
	{
		this.host=host;
	}

	@Override
	public DnsResult call() throws Exception
	{
		final InetAddress iaHost = InetAddress.getByName(host);
        System.out.println("host.isReachable(1000) = " + iaHost.isReachable(1000));
		return new DnsResult(1);
	}
}