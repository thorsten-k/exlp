package net.sf.exlp.addon.exim.data.facade.exlp;

import net.sf.exlp.addon.common.data.facade.exlp.ExlpFacade;
import net.sf.exlp.addon.exim.data.ejb.ExlpEmail;

public interface ExlpEximFacade extends ExlpFacade
{
	ExlpEmail fEmail(String email);
	
}
