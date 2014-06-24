package com.naicha.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 日期工具类
 * @author: ethanchiu@126.com
 * @date: 2013-7-26
 */
public class DateUtils
{
	
	/** 日期格式*/
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * @Description: 获取当前系统日期
	 * @author: ethanchiu@126.com
	 * @date: May 5, 2014
	 * @return
	 */
	public static String getCurrentDate()
	{
		return getCurrentDate(DATE_FORMAT);
	}
	
	/**
	 * @Description: 获取当前系统日期
	 * @author: ethanchiu@126.com
	 * @date: May 5, 2014
	 * @param format 日期格式
	 * @return
	 */
	public static String getCurrentDate(String format)
	{
		return time2format(System.currentTimeMillis(), format);
	}
	
	/**
	 * @Description: 时间戳转换成日期
	 * @author: ethanchiu@126.com
	 * @date: May 5, 2014
	 * @param milSeconds 时间按戳
	 * @param format 时间格式
	 * @return
	 */
	public static String time2format(long milSeconds, String format)
	{
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date(milSeconds));
	}
	
	/**
	 * 返回年月日
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String formatYMDHM(Timestamp timestamp)
	{
		if ( timestamp != null )
		{
			// return new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
			return new SimpleDateFormat("yyyyMMdd").format(timestamp);
		}
		return "2012-01-01";
	}

	/**
	 * 指定时间的上个月
	 * 
	 * @param currentTime
	 * @return
	 */
	public static Timestamp getLastMonthByTime(long currentTime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currentTime);
		calendar.add(Calendar.MONTH, -1);

		return new Timestamp(calendar.getTimeInMillis());

	}

	/**
	 * 获取当前时间的上一个月的时间
	 * 
	 * @return
	 */
	public static long getLastMonthTimeForSeconds()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

		return (calendar.getTimeInMillis() / 1000);
	}

	/**
	 * 获取当前long型时间
	 * 
	 * @return
	 */
	public static long getCurTimeForSeconds()
	{
		return (System.currentTimeMillis() / 1000);
	}

	/**
	 * 根据年份，月份，日期转换成Timestamp
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static Timestamp getTimestamp(int year, int monthOfYear, int dayOfMonth)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, monthOfYear);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取年月，降序排列
	 * 
	 * @param count
	 *            日期个数
	 * @param splitDay
	 *            基准日期，当前日期小于等于splitDay，取值不含当月；当前日期大于splitDay，取值包含当月
	 * @return
	 */
	public static ArrayList<String> getYearAndMonth(int count, int splitDay)
	{
		Calendar calendar = Calendar.getInstance();
		// 获取当前日期，如：18
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if ( day <= splitDay )
		{
			// 当前日期小于splitDay基准日期，取值不包含当月，所以先去掉
			calendar.add(Calendar.MONTH, -1);
		}

		ArrayList<String> ymList = new ArrayList<String>();
		for (int i = count; i > 0; i--)
		{
			ymList.add(new SimpleDateFormat("yyyyMM").format(calendar.getTime()));
			calendar.add(Calendar.MONTH, -1);
		}
		return ymList;
	}

	/**
	 * 获取当前时间，如：20121225
	 * 
	 * @version Dec 25, 2012
	 * @return
	 */
	public static String getCurrDate()
	{
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());

	}

	/**
	 * 跟下面的方法区别是他获取的是日期是1号
	 * 获取与当前时间currDate(20121125)相差delta个年月日的时间，如：当前为：20121225
	 * ，delta分别为0,-1,0，则结果为：20121125
	 * 
	 * @version Dec 25, 2012
	 * @param deltaYear
	 * @param deltaMonth
	 * @return
	 */
	public static String getDeltaToCurrDate(String currDate, int deltaYear, int deltaMonth)
	{
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date;
		try
		{
			date = sdf.parse(currDate);
			calendar.setTime(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		calendar.add(Calendar.YEAR, deltaYear);
		calendar.add(Calendar.MONTH, deltaMonth);
		calendar.set(Calendar.DATE, 1);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取与当前时间currDate(20121125)相差delta个年月日的时间，如：当前为：20121225，delta分别为0,-1,0，
	 * 则结果为：20121125
	 * 
	 * @version Dec 25, 2012
	 * @param deltaYear
	 * @param deltaMonth
	 * @param deltaDate
	 * @return
	 */
	public static String getDeltaToCurrDate(String currDate, int deltaYear, int deltaMonth, int deltaDate)
	{
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date;
		try
		{
			date = sdf.parse(currDate);
			calendar.setTime(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		calendar.add(Calendar.YEAR, deltaYear);
		calendar.add(Calendar.MONTH, deltaMonth);
		calendar.add(Calendar.DATE, deltaDate);
		return sdf.format(calendar.getTime());
	}

	/**
	 * Tests if time1 is after time2
	 * 
	 * @version Dec 27, 2012
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean compareTime1After2(String time1, String time2)
	{
		Date date1 = str2Date(time1);
		Date date2 = str2Date(time2);
		return date1.after(date2);
	}

	public static Date str2Date(String currDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try
		{
			date = sdf.parse(currDate);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将字符串的日期转换为long型日期
	 * 
	 * @param strDate
	 * @return
	 */
	public static long getSecondsTimeDate(String strDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;
		try
		{
			date = sdf.parse(strDate);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return date.getTime() / 1000;
	}

	/**
	 * 格式化日期20120912--->2012-09-12
	 * 
	 * @param currDate
	 * @return
	 */
	public static String fomatDate(String currDate)
	{
		if ( currDate == null || currDate.length() == 0 )
			return "";
		String symbol = "-";
		String time = currDate.substring(0, 4) + symbol + currDate.substring(4, 6) + symbol + currDate.substring(6, currDate.length());
		return time;
	}

	/**
	 * 格式化时间091234---》09:12:34
	 * 
	 * @param currTime
	 * @return
	 */
	public static String fomatTime(String currTime)
	{
		if ( currTime == null || currTime.length() == 0)
			return "";
		String symbol = ":";
		String time = currTime.substring(0, 2) + symbol + currTime.substring(2, 4) + symbol + currTime.substring(4, 6);
		return time;
	}

	/**
	 * 获取当前时间的字符串
	 * 
	 * @return
	 */
	public static String getCurrentyyyyMMDDHHmmss()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

	/**
	 * @description:获取当天的时间段
	 * @return
	 */
	public static String getCurTimeSection()
	{
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		String timeName = "晚上";
		if ( hour > 0 && hour < 5 )
		{
			timeName = "凌晨";
		}
		else if ( hour > 5 && hour < 11 )
		{
			timeName = "早上";
		}
		else if ( hour > 11 && hour < 13 )
		{
			timeName = "中午";
		}
		else if ( hour > 13 && hour < 17 )
		{
			timeName = "下午";
		}
		else if ( hour > 17 && hour < 19 )
		{
			timeName = "傍晚";
		}

		return timeName;
	}
}
