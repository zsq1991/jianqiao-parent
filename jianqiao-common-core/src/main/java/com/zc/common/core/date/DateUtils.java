package com.zc.common.core.date;

import com.zc.common.core.utils.MyObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @create-time 2011-12-15 下午:14:18
 * 
 */
public class DateUtils {

	public static String DATE_PATTERN_01 = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_PATTERN_02 = "yyyy-MM-dd";
	public static String DATE_PATTERN_03 = "yyyy年MM月dd日";
	public static String DATE_PATTERN_04 = "MM月dd日";
	public static String DATE_PATTERN_05 = "yyyy-MM-dd-HH-mm";
	public static String DATE_PATTERN_06 = "yyyy-MM";
	public static String DATE_PATTERN_07 = "yyyy-MM-dd HH:mm";
	/**
	 * 根据传入的时间获取当前星期几
	 * 
	 * @param date 时间
	 * @return String
	 */
	public static String getWeekDayStrByDate(Date date) {
		String weekString = "";
        final String[] dayNames = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		weekString = dayNames[dayOfWeek - 1];
		return weekString;
	}

	/**
	 * 根据传入的时间获取当前星期几
	 * 
	 * @param date 时间
	 * @return int
	 */
	public static int getWeekDayIntByDate(Date date) {
		int weekInt = 0;
        final int[] dayNameInts = {7, 1, 2, 3, 4, 5, 6};
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		weekInt = dayNameInts[dayOfWeek - 1];
		return weekInt;
	}

	/**
	 * 两个日期相减，取天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long daysBetween(Date date1, Date date2) {
		if (date2 == null) {
            date2 = new Date();
        }
		long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 比较两个日期 if date1<=date2 return true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(String date1, String date2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);
			return !d1.after(d2);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 字符型转换成日期型
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static Date dateFormat(String date, String dateFormat) {
		if (date == null) {
            return null;
        }
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		if (date != null) {
			try {
				return format.parse(date);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 使用默认格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static Date dateFormat(String date) {
		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期型转换成字符串
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateFormat(Date date, String dateFormat) {
		if (date == null) {
            return null;
        }
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	/**
	 * 使用默认格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取昨天
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Date date = new Date();
		long time = (date.getTime() / 1000) - 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	/**
	 * 获取一个星期以前的那一天
	 * 
	 * @return
	 */
	public static Date getWeekAgo() {
		Date date = new Date();
		long time = (date.getTime() / 1000) - 7 * 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	/**
	 * 获取多少天以前
	 * 
	 * @param interval 天数
	 * @return
	 */
	public static String getDaysAgo(int interval) {
		Date date = new Date();
		long time = (date.getTime() / 1000) - interval * 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.format(date);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * 获取明天
	 * 
	 * @return
	 */
	public static Date getTomorrow() {
		Date date = new Date();
		long time = (date.getTime() / 1000) + 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	/**
	 * 获取之前的一个星期或者一个月
	 * 
	 * @param range 为：week或者month
	 * @return
	 */
	public static Date getBeforeDate(String range) {
		Calendar today = Calendar.getInstance();
		String week="week";
		String month="month";
		if (week.equalsIgnoreCase(range)) {
            today.add(Calendar.WEEK_OF_MONTH, -1);
        } else if (month.equalsIgnoreCase(range)) {
            today.add(Calendar.MONTH, -1);
        } else {
            today.clear();
        }
		return today.getTime();
	}

	/**
	 * 获取这个星期开始的一天
	 * 
	 * @return
	 */
	public static Date getThisWeekStartTime() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DAY_OF_WEEK, today.getFirstDayOfWeek());
		Calendar weekFirstDay = Calendar.getInstance();
		weekFirstDay.clear();
		weekFirstDay.set(Calendar.YEAR, today.get(Calendar.YEAR));
		weekFirstDay.set(Calendar.MONTH, today.get(Calendar.MONTH));
		weekFirstDay.set(Calendar.DATE, today.get(Calendar.DATE));
		return weekFirstDay.getTime();
	}

	/**
	 * 获取今天的日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getToday(String format) {
		String result = "";
		try {
			Date today = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
			result = simpleFormat.format(today);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 哪一年那个月的开始日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getStartDay(int year, int month) {
		Calendar today = Calendar.getInstance();
		today.clear();
		today.set(Calendar.YEAR, year);
		today.set(Calendar.MONTH, month - 1);
		today.set(Calendar.DAY_OF_MONTH, 1);
		return today.getTime();
	}

	/**
	 * 判断当前时间是否在在两个时间之间
	 * 
	 * @param startDate 开始时间
	 * @param endDate　结束时间
	 * @return　
	 */
	public static boolean nowDateBetweenStartDateAndEndDate(Date startDate, Date endDate) {
		boolean bool = false;
		Date curDate = new Date();
		if (curDate.after(startDate) && curDate.before(endDate)) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 判断当前时间是否在date之后
	 * 
	 * @param date
	 * @return　
	 */
	public static boolean nowDateAfterDate(Date date) {
		boolean bool = false;
		Date curDate = new Date();
		if (curDate.after(date)) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 根据生日去用户年龄
	 * 
	 * @param birthday
	 * @return int
	 * @exception
	 * @author 豆皮
	 * @Date Apr 24, 2008
	 */
	public static int getUserAge(Date birthday) {
		if (birthday == null) {
            return 0;
        }
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		// 给时间赋值
		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		return yearNow - yearBirth;
	}

	/**
	 * 获取一天当中的小时数
	 * 
	 * @return
	 */
	public static int getDayOfHour() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前10位时间戳
	 * 
	 * @return
	 */
	public static int getTimeStamp10() {
		Long time = System.currentTimeMillis();
		return Integer.parseInt(StringUtils.substring(MyObjectUtils.toString(time), 0, 10));
	}
}
