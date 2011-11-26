package net.sf.exlp.util.data.facade.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.data.facade.exlp.ExlpCheckoutFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacadeCheckoutContainer
{
	final static Logger logger = LoggerFactory.getLogger(FacadeCheckoutContainer.class);
	
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