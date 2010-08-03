package net.sf.exlp.parser;

public class PatternFactory
{
	public static String ipPattern="[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+";
	public static String urlPattern="[\\!\\[\\]\\(\\)%&:;~|@'#\\/?\\+=\\^$\\w\\,\\.\\-]*";
	public static String macPatter="[a-fA-F\\d]*:[a-fA-F\\d]*:[a-fA-F\\d]*:[a-fA-F\\d]*:[a-fA-F\\d]*:[a-fA-F\\d]*";
	
	//Exim
	public static String eximDate="([0-9]+)-([0-9]+)-([0-9]+)";
	public static String eximTime="([0-9]+):([0-9]+):([0-9]+)";
}
