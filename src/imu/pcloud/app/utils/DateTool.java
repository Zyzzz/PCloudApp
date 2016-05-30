package imu.pcloud.app.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
    static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
    static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public static String timeToString(Time time) {
        if (time == null)
            return null;
        return timeFormat.format(time);
    }

    public static Time stringToTime(String string) {
        try {
            return new Time(timeFormat.parse(string).getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String datetimeToString(Date datetime) {
        if (datetime == null)
            return null;
        return datetimeFormat.format(datetime);
    }

    public static Date stringToDatetime(String string) {
        try {
            return datetimeFormat.parse(string);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date) {
        if (date == null)
            return null;
        return dateFormat.format(date);
    }

    public static Date stringToDate(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
