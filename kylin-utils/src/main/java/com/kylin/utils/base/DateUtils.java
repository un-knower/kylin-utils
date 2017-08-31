package com.kylin.utils.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author zhouqingda
 *
 */
public class DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	public static String getDateFormat(String format,Date date){
		SimpleDateFormat sdf =   new SimpleDateFormat(format);
		String sysDate="";
		try{
		sysDate=sdf.format(date);
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return sysDate;
	}
	
	public static Date parseDate(String format,String date){
		SimpleDateFormat sdf =   new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
