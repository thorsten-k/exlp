package net.sf.exlp.addon.common.data.facade.exlp;

import java.util.List;

import net.sf.exlp.addon.common.data.ejb.ExlpCountry;
import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.addon.common.data.exception.ExlpNotFoundException;

public interface ExlpCommonFacade extends ExlpFacade
{
	//Host
	ExlpHost fExlpHost(String ip) throws ExlpNotFoundException;
	List<ExlpHost> fExlpHostWihtoutCountry(int limit);
	
	//Country
	ExlpCountry fCountryByCode(String code) throws ExlpNotFoundException;
}