package imu.pcloud.app.utils;

import android.webkit.DateSorter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//	public static String getRealDate(String dateString) {
//		if(dateString == null)
//			return null;
//		String monthStr = dateString.substring(5, 7);
//		int month = Integer.parseInt(monthStr);
//		month++;
//		monthStr = month < 10 ? "0" + month : "" + month;
//		String result = dateString.substring(0, 5)  + monthStr + dateString.substring(7);
//		return result;
//	}
	
}
