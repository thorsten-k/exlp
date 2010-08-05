package net.sf.exlp.addon.exim.event;

import java.util.Date;
import java.util.Map;

import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.addon.common.data.exception.ExlpNotFoundException;
import net.sf.exlp.addon.common.data.facade.exlp.ExlpCommonFacade;
import net.sf.exlp.addon.exim.data.ejb.ExlpEmail;
import net.sf.exlp.addon.exim.data.ejb.ExlpGreylist;
import net.sf.exlp.addon.exim.data.facade.exlp.ExlpEximFacade;
import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EximGreylistEvent extends AbstractEvent implements LogEvent
{
	static Log logger = LogFactory.getLog(EximGreylistEvent.class);
	static final long serialVersionUID=1;
	
	private ExlpEmail from, rcpt;
	private ExlpHost host;
	private Date logRecord;
	
	public EximGreylistEvent(ExlpEmail from, ExlpEmail rcpt, ExlpHost host, Date logRecord)
	{
		this.from=from;
		this.rcpt=rcpt;
		this.host=host;
		this.logRecord=logRecord;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("from: "+from.getEmail());
		logger.debug("rcpt: "+rcpt.getEmail());
		logger.debug("host: "+host.getIp());
		logger.debug("logRecord: "+logRecord);
	}
	
	public boolean persist(Map<String,Object> mapFacades)
	{
		boolean result = false;
		if(mapFacades.containsKey(ExlpCommonFacade.class.getSimpleName()) && mapFacades.containsKey(ExlpEximFacade.class.getSimpleName()))
		{
			ExlpCommonFacade fExlp = (ExlpCommonFacade)mapFacades.get(ExlpCommonFacade.class.getSimpleName());
			ExlpEximFacade fExim = (ExlpEximFacade)mapFacades.get(ExlpEximFacade.class.getSimpleName());
			
			ExlpHost dbHost = fHost(fExlp);
			ExlpEmail dbFrom = fExim.fEmail(from.getEmail());
			ExlpEmail dbRcpt = fExim.fEmail(rcpt.getEmail());
			
			ExlpGreylist exlpGreylist;
			try {exlpGreylist=fExim.fGreylist(logRecord, dbFrom, dbRcpt);}
			catch (ExlpNotFoundException e)
			{
				exlpGreylist = new ExlpGreylist();
				exlpGreylist.setFrom(dbFrom);
				exlpGreylist.setRcpt(dbRcpt);
				exlpGreylist.setHost(dbHost);
				exlpGreylist.setRecord(logRecord);
				fExim.newObject(exlpGreylist);
			}
		}
		else
		{
			logger.warn("Facade not available. Cannot persist");
		}
		return result;
	}
	
	private ExlpHost fHost(ExlpCommonFacade fExlp)
	{
		ExlpHost dbHost;
		try
		{
			dbHost = fExlp.fExlpHost(host.getIp());
		}
		catch (ExlpNotFoundException e)
		{
			dbHost = (ExlpHost)fExlp.newObject(host);
		}
		return dbHost;
	}
}