package net.sf.exlp.addon.exim;

import java.util.Date;

import net.sf.exlp.addon.exim.data.facade.exlp.ExlpEximFacade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EximRafDateSeeker
{
	static Log logger = LogFactory.getLog(EximRafDateSeeker.class);
	
	private ExlpEximFacade fExim;
	
	public EximRafDateSeeker(ExlpEximFacade fExim)
	{
		this.fExim=fExim;
	}
	
	public void seek()
	{
		Date lastEximDbRecord = new Date();
		logger.debug(lastEximDbRecord);
	}
}
