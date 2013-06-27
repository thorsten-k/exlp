package net.sf.exlp.addon.monitoring.dns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnsResult
{
	final static Logger logger = LoggerFactory.getLogger(DnsResult.class);
	
	private int result;

	public DnsResult(int result)
	{
		this.result=result;
	}
	
	public int getResult() {return result;}
}
