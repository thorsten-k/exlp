package net.sf.exlp.util.data.facade;

import javax.naming.InitialContext;

public interface FacadeFactory
{
	InitialContext getContext();
	String getContextPrefix();
}
