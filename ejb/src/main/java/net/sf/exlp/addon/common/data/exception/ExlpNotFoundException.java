package net.sf.exlp.addon.common.data.exception;

import java.io.Serializable;

public class ExlpNotFoundException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public ExlpNotFoundException() 
	{ 
	} 
 
	public ExlpNotFoundException(String s) 
	{ 
		super(s); 
	} 
}
