package com.alen.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class DateUtils {
	// 日期增加年
	public static Date addYears(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, num);
		return c.getTime();
	}

	// 日期增加分钟
	public static Date addMinuts(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, num);
		return c.getTime();
	}

	// 日期增加秒
	public static Date addSeconds(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, num);
		return c.getTime();
	}

	// 日期增加毫秒
	public static Date addMilliSeconds(Date date, long num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MILLISECOND, (int) num);
		return c.getTime();
	}

	// 日期增加小时
	public static Date addHours(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, num);
		return c.getTime();
	}

	// 日期增加天数
	public static Date addDays(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, num);
		return c.getTime();
	}

	// 日期增加年月数
	public static Date addMonths(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, num);
		return c.getTime();
	}

	// 格式化日期
	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	// 格式化日期
	public static String formatDate(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	// 格式化日期
	public static String formatDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	// 获取2个日期之间的月数
	public static int betweenMonth(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return 0;
		int months = 0;
		Calendar calbegin = Calendar.getInstance();
		calbegin.setTime(startDate);
		do {
			if (endDate.getTime() >= calbegin.getTimeInMillis())
				months++;
			calbegin.add(Calendar.MONTH, 1);
		} while (endDate.getTime() >= calbegin.getTimeInMillis());
		return months;
	}

	// 获取2个日期之间的天数
	public static int betweenDays(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return 0;
		int days = 0;
		Calendar calbegin = Calendar.getInstance();
		calbegin.setTime(startDate);
		Calendar calend = Calendar.getInstance();
		calend.setTime(endDate);
		calend.set(Calendar.HOUR_OF_DAY, 23);
		calend.set(Calendar.MINUTE, 59);
		calend.set(Calendar.SECOND, 59);
		calend.set(Calendar.MILLISECOND, 999);
		do {
			if (calend.getTimeInMillis() >= calbegin.getTimeInMillis())
				days++;
			calbegin.add(Calendar.DATE, 1);
		} while (calend.getTimeInMillis() >= calbegin.getTimeInMillis());
		return days;
	}

	// 比较2个字符串的日期
	public static boolean beforeDate(String start, String end) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date startTime = df.parse(start);
			Date endTime = df.parse(end);
			if (startTime.before(endTime))
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 获取指定日期所在月份的最后一天的日期
	public static Date getLastDayOfMonth(String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cDay = Calendar.getInstance();
		try {
			cDay.setTime(df.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int lastDay = cDay.getActualMaximum(Calendar.DAY_OF_MONTH);
		cDay.set(Calendar.DAY_OF_MONTH, lastDay);
		return cDay.getTime();
	}

	public static Date getLastDayOfMonth(Date time) {
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(time);
		int lastDay = cDay.getActualMaximum(Calendar.DAY_OF_MONTH);
		cDay.set(Calendar.DAY_OF_MONTH, lastDay);
		return cDay.getTime();
	}

	// 获取指定日期所在月份的第一天的日期
	public static Date getFirstDayOfMonth(String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cDay = Calendar.getInstance();
		try {
			cDay.setTime(df.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cDay.set(Calendar.DAY_OF_MONTH, 1);
		return cDay.getTime();
	}

	public static Date getFirstDayOfMonth(Date time) {
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(time);
		cDay.set(Calendar.DAY_OF_MONTH, 1);
		return cDay.getTime();
	}

	// 获取指定时间的年月日的时间
	public static Date getDate(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	// 字符串日期转换为date日期
	public static Date getDate(String time, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
