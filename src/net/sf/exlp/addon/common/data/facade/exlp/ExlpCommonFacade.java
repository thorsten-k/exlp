package net.sf.exlp.addon.common.data.facade.exlp;

import java.util.List;

import net.sf.exlp.addon.common.data.ejb.ExlpHost;

public interface ExlpCommonFacade extends ExlpFacade
{
	ExlpHost fExlpHost(String ip);
	List<ExlpHost> fExlpHostWihtoutCountry(int limit);
}