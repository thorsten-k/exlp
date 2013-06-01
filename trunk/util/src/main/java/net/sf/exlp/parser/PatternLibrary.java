package net.sf.exlp.parser;

public class PatternLibrary
{
	public static String hostPattern="[\\w\\.-]+";
	public static String ipPattern="[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+";
	public static String urlPattern="[\\!\\[\\]\\(\\)%&:;~|@'#\\/?\\+=\\^$\\w\\,\\.\\-]*";
	public static String macPatter="[a-fA-F\\d]*:[a-fA-F\\d]*:[a-fA-F\\d]*:[a-fA-F\\d]*:[a-fA-F\\d]*:[a-fA-F\\d]*";
	public static String email="[\\w\\.\\-=+{}]+@"+hostPattern;
	
	//Exim
	public static String eximDate="([0-9]+)-([0-9]+)-([0-9]+)";
	public static String eximTime="([0-9]+):([0-9]+):([0-9]+)";
	public static String eximId="([0-9a-zA-Z]+)-([0-9a-zA-Z]+)-([0-9a-zA-Z]+)";
	public static String eximPrefix = eximDate+" "+eximTime+" ";
}
