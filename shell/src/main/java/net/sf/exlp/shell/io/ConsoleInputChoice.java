package net.sf.exlp.shell.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleInputChoice
{
	final static Logger logger = LoggerFactory.getLogger(ConsoleInputChoice.class);
	
	public static <E extends Enum<E>> E getEnumForChoice(E[] choice) throws IllegalArgumentException
	{
		int index=-1;
		String inputString=null;
		for(int i=0;i<choice.length;i++)
		{
			System.out.println("\t("+(i+1)+") "+choice[i]);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			inputString = br.readLine();
			index = Integer.parseInt(inputString);
		}
		catch (IOException ioe) {ioe.printStackTrace();}
		catch (NumberFormatException nfe) {throw new IllegalArgumentException("Input '"+inputString+"' is not valid");}
		
		if(index<1 || index > choice.length)
		{
			throw new IllegalArgumentException("Input '"+inputString+"' is not valid");
		}
		
		return choice[index-1]; 
	}
	
	public static <T extends Object> T getListItemForChoice(List<T> list) throws IllegalArgumentException
	{
		Map<T,String> map = new Hashtable<T,String>();
		for(T t : list){map.put(t, t.toString());}
		return getListItemForChoice(list,map); 
	}
	
	public static <T extends Object> T getListItemForChoice(List<T> list, Map<T,String> map) throws IllegalArgumentException
	{
		int index=-1;
		String inputString=null;
		for(int i=0;i<list.size();i++)
		{
			System.out.println("\t("+(i+1)+") "+map.get(list.get(i)));
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			inputString = br.readLine();
			index = Integer.parseInt(inputString);
		}
		catch (IOException ioe) {ioe.printStackTrace();}
		catch (NumberFormatException nfe) {throw new IllegalArgumentException("Input '"+inputString+"' is not valid");}
		
		if(index<1 || index > list.size())
		{
			throw new IllegalArgumentException("Input '"+inputString+"' is not valid");
		}
		
		return list.get(index-1); 
	}
	
	public static String readLine(String out)
	{
		String result="";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			System.out.println(out);
			result = br.readLine();
		}
		catch (IOException ioe) {ioe.printStackTrace();}
		return result;
	}
	
	public static int readInt(String out)
	{
		String s = readLine(out);
		int result=0;
		try	{result = Integer.parseInt(s);}
		catch (NumberFormatException nfe) {throw new IllegalArgumentException("Input '"+s+"' not allowed");}
		return result;
	}
	
	public static double readDouble(String out)
	{
		String s = readLine(out);
		double result=0;
		try	{result = Double.parseDouble(s);}
		catch (NumberFormatException nfe) {throw new IllegalArgumentException("Input '"+s+"' not allowed");}
		return result;
	}
}
