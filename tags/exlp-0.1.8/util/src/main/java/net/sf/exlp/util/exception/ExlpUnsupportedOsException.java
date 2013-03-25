package net.sf.exlp.util.exception;

import java.io.Serializable;

public class ExlpUnsupportedOsException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public ExlpUnsupportedOsException() 
	{ 
	} 
 
	public ExlpUnsupportedOsException(String s) 
	{ 
		super(s); 
	} 
}
