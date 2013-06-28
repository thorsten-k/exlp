package net.sf.exlp.monitor.net.icmp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcmpTask implements Callable<IcmpResult>
{
	final static Logger logger = LoggerFactory.getLogger(IcmpTask.class);
	
	private String host;
	
	public IcmpTask(String host)
	{
		this.host=host;
	}

	@Override
	public IcmpResult call()
	{
		IcmpResult icmpResult = new IcmpResult();
		icmpResult.setRecord(new Date());
		
		try
		{
			InetAddress iaHost = InetAddress.getByName(host);
			
			long startTime = System.currentTimeMillis();
			boolean isReachable = iaHost.isReachable(10000);
			icmpResult.setDuration(System.currentTimeMillis()-startTime);
	        if(isReachable){icmpResult.setCode(IcmpResult.Code.REACHABLE);}
	        else{icmpResult.setCode(IcmpResult.Code.TIMEOUT);}
	        
		}
		catch (UnknownHostException e)
		{
			icmpResult.setCode(IcmpResult.Code.UNKNOWN_HOST);
		}
		catch (IOException e)
		{
			icmpResult.setCode(IcmpResult.Code.ERROR);
		}
	    
		return icmpResult;
	}
}