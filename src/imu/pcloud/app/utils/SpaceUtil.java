package imu.pcloud.app.utils;

/**
 * Created by guyu on 2016/6/5.
 */
public class SpaceUtil {
    public static String getSpace(int num) {
        String space = new String("");
        for(int i = 0; i < num; i++)
            space = space + " ";
        return space;
    }
}
