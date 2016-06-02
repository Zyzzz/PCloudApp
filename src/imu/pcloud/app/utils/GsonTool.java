package imu.pcloud.app.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imu.pcloud.app.adapter.TimestampTypeAdapter;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/5/23.
 */
public class GsonTool {
    private static Gson gson = null;
    public static Gson getGson() {
        if(gson == null)
            init();
        return gson;
    }
    private GsonTool() {

    }
    public static void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter());
        gson = gsonBuilder.create();
    }
}
