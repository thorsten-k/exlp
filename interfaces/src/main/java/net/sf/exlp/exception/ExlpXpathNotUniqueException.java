package net.sf.exlp.exception;

import java.io.Serializable;

public class ExlpXpathNotUniqueException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public ExlpXpathNotUniqueException() 
	{ 
	} 
 
	public ExlpXpathNotUniqueException(String s) 
	{ 
		super(s); 
	} 
}