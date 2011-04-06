package net.sf.exlp.event.exception;

import java.io.Serializable;

public class HandlerException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public HandlerException() 
	{ 
	} 
 
	public HandlerException(String s) 
	{ 
		super(s); 
	} 
}
