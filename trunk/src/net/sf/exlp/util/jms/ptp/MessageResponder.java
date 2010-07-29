package net.sf.exlp.util.jms.ptp;

import java.io.Serializable;

public interface MessageResponder
{
	public Serializable respondObject(Object o);
}
