package org.jb.persistence.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String fromDate(Date date) {
		return fromDate(date, "dd/mm/yyyy");
	}
	
	public static String fromDate(Date date, String formatString) {
		if(date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat(formatString);  
		return format.format(date);
	}
	
	public static Date toDate(String date) {
		if(date == null || date.isEmpty())
			return null;
		return toDate(date, "dd/mm/yyyy");
	}
	
	public static Date toDate(String dateString, String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);  
		try {
			return new Date(format.parse(dateString).getTime());
		} catch (ParseException e) {
			return null;
		} 
	}
}
