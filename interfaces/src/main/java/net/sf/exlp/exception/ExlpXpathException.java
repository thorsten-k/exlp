package net.sf.exlp.exception;

import java.io.Serializable;

public class ExlpXpathException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public ExlpXpathException() 
	{ 
	} 
 
	public ExlpXpathException(String s) 
	{ 
		super(s); 
	} 
}