package net.sf.exlp.addon.exim.data.facade.exlp;

import java.util.Date;

import net.sf.exlp.addon.common.data.exception.ExlpNotFoundException;
import net.sf.exlp.addon.common.data.facade.exlp.ExlpFacade;
import net.sf.exlp.addon.exim.data.ejb.ExlpEmail;
import net.sf.exlp.addon.exim.data.ejb.ExlpGreylist;

public interface ExlpEximFacade extends ExlpFacade
{
	//Email
	ExlpEmail fEmail(String email);
	
	//Greylist
	ExlpGreylist fGreylist(Date record, ExlpEmail from, ExlpEmail rcpt) throws ExlpNotFoundException;
}
