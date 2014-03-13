package net.sf.exlp.util.io;

public class FilenameIllegalCharRemover
{
	private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0',
		                                               '\f', '`', '?', '*', '\\',
		                                               '<', '>', '|', '\"', ':' };
	
	public static String convert(String fileName)
	{
		for(char c : ILLEGAL_CHARACTERS)
		{
			fileName = fileName.replace(c, "_".toCharArray()[0]);
		}
		return fileName;
	}
}
