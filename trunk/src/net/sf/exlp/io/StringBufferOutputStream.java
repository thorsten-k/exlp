package net.sf.exlp.io;

import java.io.OutputStream;

import org.apache.log4j.Logger;

public class StringBufferOutputStream extends OutputStream
{
	static Logger logger = Logger.getLogger(StringBufferOutputStream.class);
	
	protected StringBuffer sb = new StringBuffer();

	public StringBufferOutputStream()
	{
		
	}

	public void close() {}

	public void flush()
	{
		logger.trace("Flushing ...");
	}

	public void write(byte[] b)
	{
		String str = new String(b);
		sb.append(str);
	}
	
	public void write(byte[] b, int off, int len)
	{
		String str = new String(b, off, len);
		sb.append(str);
	}

	public void write(int b)
	{
		String str = Integer.toString(b);
		sb.append(str);
	}

	public StringBuffer getStringBuffer()
	{
		return sb;
	}
}
