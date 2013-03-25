package net.sf.exlp.util.net.jms.ptp;

import java.io.Serializable;

public interface MessageResponder
{
	public Serializable respondObject(Object o);
}
