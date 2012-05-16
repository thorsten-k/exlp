package net.sf.exlp.util.net.ejb;

import javax.naming.InitialContext;

public interface ExlpFacadeFactory
{
	InitialContext getContext();
	String getContextPrefix();
}
