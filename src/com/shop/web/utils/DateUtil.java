package com.shop.web.utils;


import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 */
public class DateUtil {

    public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_FORMAT_PATTERN_NO_SECOND = "yyyy-MM-dd HH:mm";

    public static final String TIME_FORMAT_PATTERN = "HH:mm:ss";

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public static final String SHORT_DATE_FORMAT_PATTERN = "yyyyMMdd";

    public static final String SQL_DATE_FORMAT_PATTERN = "yyyy/MM/dd";

    public static final String MONTH_PATTERN = "yyyyMM";

    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);

    public static final DateFormat DATE_TIME_FORMAT_NO_SECOND = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN_NO_SECOND);

    public static final DateFormat TIME_FORMAT = new SimpleDateFormat(TIME_FORMAT_PATTERN);

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    public static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(SHORT_DATE_FORMAT_PATTERN);

    public static final DateFormat SQL_DATE_FORMAT = new SimpleDateFormat(SQL_DATE_FORMAT_PATTERN);

    public static Calendar currentDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static String currentDateString() {
        return DATE_FORMAT.format(new Date());
    }

    public static String currentDateTimeString() {
        return DATE_TIME_FORMAT.format(new Date());
    }

    public static Date tryParseDateTime(String dateTime) {
        try {
            return DATE_TIME_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date tryParseDate(String dateTime) {
        try {
            return SHORT_DATE_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }    
    
        
    public static String formatDateTime(Date date) {
        if (date != null) {
            return DATE_TIME_FORMAT.format(date);
        }
        return "";
    }

    public static String format2SqlDateTime(Date date) {
        if (date != null) {
            return SQL_DATE_FORMAT.format(date);
        }
        return "";
    }

    public static String format2ShortDateString(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static String format2Date(Date date) {
        if (date != null)
            return DATE_FORMAT.format(date);
        return "";
    }

    public static String format2Date(java.sql.Date date) {
        if (date != null)
            return format2Date(new Date(date.getTime()));
        return "";
    }

    /**
     * yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String formatDateTimeNoSecond(Date date) {
        if (date != null) {
            return DATE_TIME_FORMAT_NO_SECOND.format(date);
        }
        return "";
    }

    public static java.sql.Date parseSqlDate(String format, String dateValueStr) {
        try {
            Date date = new SimpleDateFormat(format).parse(dateValueStr);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.MILLISECOND, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR_OF_DAY, 0);

            return new java.sql.Date(c.getTimeInMillis());

        } catch (Exception e) {
            return null;
        }
    }

    public static java.sql.Date parseSqlDate(String dateValueStr) {
        return parseSqlDate(SQL_DATE_FORMAT_PATTERN, dateValueStr);
    }

    public static java.sql.Date getToday() {
        return new java.sql.Date(new Date().getTime());
    }

    public static Date getCurrentDateTimeFromTime(String timeString) {
        try {
            Date date = TIME_FORMAT.parse(timeString);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);

            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, c1.get(Calendar.HOUR_OF_DAY));
            c2.set(Calendar.MINUTE, c1.get(Calendar.MINUTE));
            c2.set(Calendar.SECOND, c1.get(Calendar.SECOND));

            return c2.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param timeStr e.g. 1:0:5
     * @return if timeStr is 1:0:5,then retun 01:00:05
     */
    public static String formatAs24HoursFormat(String timeStr) {
        try {
            TIME_FORMAT.parse(timeStr);
            String[] timeArr = timeStr.split(":");
            Integer hour = Integer.parseInt(timeArr[0]);
            Integer min = Integer.parseInt(timeArr[1]);
            Integer sec = Integer.parseInt(timeArr[2]);
            if (hour < 0 || hour > 23)
                return "";

            if (min < 0 || min > 60)
                return "";

            if (sec < 0 || sec > 60)
                return "";

            return String.format("%s:%s:%s",
                    StringUtils.leftPad(timeArr[0], 2, '0'),
                    StringUtils.leftPad(timeArr[1], 2, '0'),
                    StringUtils.leftPad(timeArr[2], 2, '0')
            );
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param dateString e.g. 2015-1-1
     * @return 20150101
     */
    public static String format2ShortDate(String dateString) {
        try {
            Date date = DATE_FORMAT.parse(dateString);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            String year = c.get(Calendar.YEAR) + "";
            String month = (c.get(Calendar.MONTH) + 1) + "";
            String day = c.get(Calendar.DAY_OF_MONTH) + "";

            return StringUtils.leftPad(year, 4, '0') + StringUtils.leftPad(month, 2, '0') + StringUtils.leftPad(day, 2, '0');
        } catch (Exception e) {
            return "";
        }

    }

    public static java.sql.Date toSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }



    public static Date getEndDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 指定日期开始时间：00:00:00:000
     *
     * @param date
     * @return
     */
    public static Date getBeginOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    /**
     * 指定日期结束时间：23:59:59:999
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        return c.getTime();
    }
    
    //计算月份
	 public static Date calMonth(String date,int month) throws ParseException, Exception {  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
	        Date dt = sdf.parse(date);  
	        Calendar rightNow = Calendar.getInstance();  
	        rightNow.setTime(dt);  
	  
	        rightNow.add(Calendar.MONTH, + month);  
	        Date dt1 = rightNow.getTime();  
//	        String reStr = sdf.format(dt1);  
	  
	        return dt1;  
	    }      
}
