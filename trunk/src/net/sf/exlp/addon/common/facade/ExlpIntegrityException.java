package net.sf.exlp.addon.common.facade;

import java.io.Serializable;

public class ExlpIntegrityException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public ExlpIntegrityException() 
	{ 
	} 
 
	public ExlpIntegrityException(String s) 
	{ 
		super( s ); 
	} 
}
