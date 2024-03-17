package net.sf.exlp.shell.io;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.exlp.test.AbstractExlpShellTest;
import org.exlp.test.ExlpShellBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliConsoleInputChoice extends AbstractExlpShellTest
{
	final static Logger logger = LoggerFactory.getLogger(CliConsoleInputChoice.class);
   
	private static enum Code {a,b,c}
	
	public CliConsoleInputChoice()
	{
		
	}
	
	public void testEnum()
	{
		Code code = ConsoleInputChoice.getEnumForChoice(Code.values());
		logger.info("Selected: "+code);
	}
	
	public void testList()
	{
		String s;
		List<String> list = new ArrayList<String>();
		Map<String,String> map = new Hashtable<String,String>();
		s = "l1"; list.add(s);map.put(s, "Text "+s);
		s = "l2"; list.add(s);map.put(s, "Text "+s);
		String result = ConsoleInputChoice.getListItemForChoice(list,map);
		logger.info("Selected: "+result);
	}
	
	public static void main(String[] args)
	{
		ExlpShellBootstrap.init();
		
		CliConsoleInputChoice cli = new CliConsoleInputChoice();
//		cli.testEnum();
		cli.testList();
	}
}