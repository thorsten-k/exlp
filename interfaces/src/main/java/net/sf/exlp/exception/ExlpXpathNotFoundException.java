package net.sf.exlp.exception;

import java.io.Serializable;

public class ExlpXpathNotFoundException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public ExlpXpathNotFoundException() 
	{ 
	} 
 
	public ExlpXpathNotFoundException(String s) 
	{ 
		super(s); 
	} 
}