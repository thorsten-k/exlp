package net.sf.exlp.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputChoiceReader
{
	final static Logger logger = LoggerFactory.getLogger(InputChoiceReader.class);
	
	public static Enum<?> getEnumForChoice(Enum<?>[] choice) throws IllegalArgumentException
	{
		int nummer=-1;
		String inputString=null;
		for(int i=0;i<choice.length;i++)
		{
			System.out.println("\t("+i+") "+choice[i]);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			inputString = br.readLine();
			nummer = Integer.parseInt(inputString);
		}
		catch (IOException ioe) {ioe.printStackTrace();}
		catch (NumberFormatException nfe) {throw new IllegalArgumentException("Eingabe "+inputString+" unzul�ssig");}
		
		if(nummer<0 || nummer >= choice.length)
		{
			throw new IllegalArgumentException("Eingabe "+inputString+" unzul�ssig");
		}
		
		return choice[nummer]; 
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
		catch (NumberFormatException nfe) {throw new IllegalArgumentException("Eingabe "+s+" unzul�ssig");}
		return result;
	}
	
	public static double readDouble(String out)
	{
		String s = readLine(out);
		double result=0;
		try	{result = Double.parseDouble(s);}
		catch (NumberFormatException nfe) {throw new IllegalArgumentException("Eingabe "+s+" unzul�ssig");}
		return result;
	}
}
