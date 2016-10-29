package com.byds.kd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.provider.ContactsContract.Data;
/*
 * 时间工具类
 */
public class DateTools {
    
    public static Date getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }
    public static String getFormatString(long time,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(getDate(time));
    }
	public static Date string2Date(String time,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(time);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		return new Date();
	}
    
    public static long getFormatString(String time,String format) {
    	Date date = string2Date(time, format);
    	return date.getTime();
    }
    
    public static String string2FomatDate(String time,String format){
    	Date date = string2Date(time, format);
    	return getFormatString(date.getTime(),"yyyy-MM-dd");
    }
    
    public static long stringDate2long(String time) {
    	return getFormatString(time, "yyyy-MM-dd");
    }
    public static String string2FomatTime(String time,String format){
    	Date date = string2Date(time, format);
    	return getFormatString(date.getTime(),"hh:mm:ss");
    }
	public static String getDistance(long before) {
	    String distance = getDistanceDay(before);
	    if("今天".equals(distance)) {
	        return getDistance(System.currentTimeMillis(), before); 
	    }
	    return distance;
	}
	public static String getDistance(long from,long end) {
	    long distance = from -end;
	    String unit="前";
	    long second=1000;
	    long min =60*second;
	    long hour =60*min;
	    long day = 24*hour;
	    if(distance<0) {
	        distance = -1*distance;
	        unit="后";
	    }
	    if((distance/second)<60) {
	        return "刚刚";
	    } else if(distance/min<60){
	        return (int)(distance/min)+"分钟"+unit;
	    } else if(distance/hour<24) {
	        return (int)(distance/hour)+"小时"+unit;
	    } else {
	        return (int)(distance/day)+"天"+unit;
	    } 
	}
	public static String getDistanceDay(long before){
		return getDistanceDay(before,System.currentTimeMillis());
	}
	public static String getDistanceDay(long before,long nowtime) {
	    Calendar now = Calendar.getInstance();
	    now.setTimeInMillis(nowtime);
	   
	    Calendar pre = Calendar.getInstance();
	    pre.setTimeInMillis(before);
	    
	    int year = now.get(Calendar.YEAR) -pre.get(Calendar.YEAR);
	    int month = now.get(Calendar.MONTH) -pre.get(Calendar.MONTH);
	    int day = now.get(Calendar.DATE) -pre.get(Calendar.DATE);
	    boolean isBefore= true;
	    if(year <0) {
	        isBefore =false;
	        year=-1*year;
	    }
	    if(year>1) {
	        return isBefore?year+"年前":year+"年后";
	    }  else if(year>0) {
            return isBefore?"去年":"明年";
        } else {
            if(month<0) {
                isBefore =false;
                month=-1*month;
            }
            if(month>0) {
                return isBefore?month+"个月前":month+"个月后";
            } else {
                if(day<0) {
                    isBefore =false;
                    day=-1*day;
                }
                if(day>1) {
                    return isBefore?day+"天前":year+"天后";
                }else if(day>0) {
                    return isBefore?"昨天":"明天";
                } else {
                    return "今天";
                }
            }
        }
	}
}
