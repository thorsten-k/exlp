package net.sf.exlp.addon.apache.event;

import net.sf.exlp.addon.apache.ejb.ExlpApache;
import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApacheEvent extends AbstractEvent implements LogEvent
{
	static Log logger = LogFactory.getLog(ApacheEvent.class);
	static final long serialVersionUID=1;
	
	private ExlpApache apache;
	private ExlpHost host;
	
	public ApacheEvent(ExlpApache apache, ExlpHost host)
	{
		this.apache=apache;
		this.host=host;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("** Rcrd\t"+apache.getRecord());
		logger.debug("** Ip\t"+host.getIp());
		logger.debug("** Req\t"+apache.getReq());
		logger.debug("** Url\t"+apache.getUrl());
		logger.debug("** Code\t"+apache.getCode());
		logger.debug("** Size\t"+apache.getSize());
	}
	
	public ExlpApache getApache() {return apache;}
	public ExlpHost getHost() {return host;}
}