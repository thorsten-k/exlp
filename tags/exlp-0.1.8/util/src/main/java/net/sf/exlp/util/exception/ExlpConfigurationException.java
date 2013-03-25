package net.sf.exlp.util.exception;

import java.io.Serializable;

public class ExlpConfigurationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public ExlpConfigurationException() 
	{ 
	} 
 
	public ExlpConfigurationException(String s) 
	{ 
		super(s); 
	} 
}
