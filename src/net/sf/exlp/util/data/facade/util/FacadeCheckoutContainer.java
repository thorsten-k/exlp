package net.sf.exlp.util.data.facade.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.data.facade.exlp.ExlpCheckoutFacade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FacadeCheckoutContainer
{
	static Log logger = LogFactory.getLog(FacadeCheckoutContainer.class);
	
	private List<ExlpCheckoutFacade> lFacade;
	
	public FacadeCheckoutContainer()
	{
		lFacade = new ArrayList<ExlpCheckoutFacade>();
	}
	
	public void add(ExlpCheckoutFacade pisFacade)
	{
		lFacade.add(pisFacade);
	}
	
	public void checkoutAll()
	{
		StringBuffer sb = new StringBuffer();
		for(ExlpCheckoutFacade facade : lFacade)
		{
			facade.checkout();
			sb.append(" "+facade.getClass().getSimpleName()+",");
		}
		logger.debug("Checked out:"+sb);
	}

}