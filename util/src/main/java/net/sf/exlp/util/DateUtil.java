package net.sf.exlp.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil
{
	final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static LocalDateTime ldtOf(String year, String month, String day, String hour, String min, String sec)
	{
		return LocalDateTime.of(Integer.valueOf(year),Integer.valueOf(month),Integer.valueOf(day),
						Integer.valueOf(hour), Integer.valueOf(min), Integer.valueOf(sec));
	}
    
	public static int getMonth(String s) throws ParseException
	{
		if(s.equals("Jan")){return 1;}
		else if(s.equals("Feb")){return 2;}
		else if(s.equals("Mar")){return 3;}
		else if(s.equals("Apr")){return 4;}
		else if(s.equals("May")){return 5;}
		else if(s.equals("Jun")){return 6;}
		else if(s.equals("Jul")){return 7;}
		else if(s.equals("Aug")){return 8;}
		else if(s.equals("Sep")){return 9;}
		else if(s.equals("Oct")){return 10;}
		else if(s.equals("Nov")){return 11;}
		else if(s.equals("Dec")){return 12;}
		throw new ParseException("No month found for: "+s,0);
	}
	
	public static XMLGregorianCalendar toXmlGc(Date d)
    {
		if(d instanceof java.sql.Date)
		{
			java.sql.Date sql = (java.sql.Date)d;
			return DateUtil.toXmlGc(sql.toLocalDate());
		}
		
		LocalDateTime ldt = DateUtil.toLocalDateTime(d);
		ZonedDateTime zdt = ZonedDateTime.of(ldt,ZoneId.systemDefault());
		return DateUtil.toXmlGc(zdt);
    }
    public static XMLGregorianCalendar toXmlGc(LocalDate ld)
    {
    	XMLGregorianCalendar xmlGc = null;
    	try{xmlGc = DatatypeFactory.newInstance().newXMLGregorianCalendar(ld.toString());}
    	catch (DatatypeConfigurationException e) {e.printStackTrace();}
    	return xmlGc;
    }
    public static XMLGregorianCalendar toXmlGc(LocalDateTime ldt)
    {
    	XMLGregorianCalendar xmlGc = null;
    	try{xmlGc = DatatypeFactory.newInstance().newXMLGregorianCalendar(ldt.format(DateTimeFormatter.ISO_DATE_TIME) );}
    	catch (DatatypeConfigurationException e) {e.printStackTrace();}
    	return xmlGc;
    }
    public static XMLGregorianCalendar toXmlGc(ZonedDateTime zdt)
    {
    	XMLGregorianCalendar xmlGc = null;
    	try
    	{
    		GregorianCalendar gregorianCalendar = GregorianCalendar.from(zdt); 
    		xmlGc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    	}
    	catch (DatatypeConfigurationException e) {e.printStackTrace();}
    	return xmlGc;
    }
	
	public static Date toDate(LocalDate localDate)
	{
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	public static Date toDate(LocalDateTime localDateTime)
	{
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	public static Date toDate(ZonedDateTime zdt)
	{
		return Date.from(zdt.toInstant());
	}
	public static Date toDate(XMLGregorianCalendar xmlGC)
	{
		return xmlGC.toGregorianCalendar().getTime();
	}
	
	public static LocalDate toLocalDate(Date date)
	{
		if(date instanceof java.sql.Date)
		{
			java.sql.Date sql = (java.sql.Date)date;
			return sql.toLocalDate();
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	public static LocalDate toLocalDate(XMLGregorianCalendar xmlGC)
	{
		return xmlGC.toGregorianCalendar().toZonedDateTime().toLocalDate();
	}
	
	public static LocalDateTime toLocalDateTime(Date date)
	{
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	public static LocalDateTime toLocalDateTime(XMLGregorianCalendar xmlGC)
	{
		return xmlGC.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
	}
	
	public static List<YearMonth> toMonthCoverage(LocalDate ldStart, LocalDate ldEnd)
	{
		YearMonth ymStart = YearMonth.from(ldStart);
		YearMonth ymEnd = YearMonth.from(ldEnd);
		
		List<YearMonth> list = new ArrayList<>();
		
		long months = ChronoUnit.MONTHS.between(ymStart,ymEnd)+1;
		logger.info(ymStart +" "+ymEnd+" months:"+months);
		for(int i=0;i<months;i++)
		{
			list.add(ymStart.plusMonths(i));
		}
		
		return list;
	}
	
	public static boolean notInPeriod(LocalDate start, LocalDate test, LocalDate end) {return !inPeriod(start,test,end);}
	public static boolean inPeriod(LocalDate start, LocalDate test, LocalDate end)
	{
		return !test.isBefore(start) && !test.isAfter(end);
	}
}