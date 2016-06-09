package imu.pcloud.app.utils;

import android.webkit.DateSorter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
	static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static String timeToString(Time time) {
		if(time == null)
			return null;
		return timeFormat.format(time);
	}
	public static Time stringToTime(String string) {
		if(string == "" || string == null)
			return null;
		try {
			return  new Time(timeFormat.parse(string).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static String datetimeToString(Date datetime) {
		if(datetime == null)
			return null;
		return datetimeFormat.format(datetime);
	}
	public static Date stringToDatetime(String string) {
		if(string == "" || string == null)
			return null;
		try {
			return datetimeFormat.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}public static String dateToString(Date date) {
		if(date == null)
			return null;
		return dateFormat.format(date);
	}
	public static Date stringToDate(String string) {
		if(string == "" || string == null)
			return null;
		try {
			return dateFormat.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断当前日期是星期几
	 * @return dayForWeek 判断结果
	 */
	static public int getWeek() {


		String Week = "";
		int weekpos = 0;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
			c.setTime(date);
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "天";
			weekpos = 7;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "一";
			weekpos = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "二";
			weekpos = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "三";
			weekpos = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "四";
			weekpos = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "五";
			weekpos = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "六";
			weekpos = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return weekpos;
	}
	
}
