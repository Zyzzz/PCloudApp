package imu.pcloud.app.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.adapter.PlanItemAdapter;
import imu.pcloud.app.model.LocalPlan;
import imu.pcloud.app.model.TimeConfig;
import imu.pcloud.app.utils.DateTool;
import imu.pcloud.app.utils.PushTool;

import java.util.ArrayList;

/**
 * Created by guyu on 2016/6/10.
 */
public class PushMsgService extends Service {

    PushTool pushTool;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    ArrayList<LocalPlan> planArrayList;
    Gson gson = new GsonBuilder().create();
    @Override
    public void onCreate() {
        pushTool = new PushTool();
        sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        initNowPlan();
        pushTool.setLocalPlanArrayList(planArrayList);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(pushTool.receiver, filter);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, PushMsgService.class);
        this.startService(localIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY_COMPATIBILITY;
        //return super.onStartCommand(intent, flags, startId);
    }

    public void initNowPlan() {
        String nowPlanString = sharedPreferences.getString("nowPlan" + getUserId(), "");
        try {
            planArrayList = gson.fromJson(nowPlanString,
                    new TypeToken<ArrayList<LocalPlan>>() {
                    }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            planArrayList = new ArrayList<LocalPlan>();
            editor.remove("nowPlan" + getUserId());
            editor.commit();
        }
        if(planArrayList == null){
            planArrayList = new ArrayList<LocalPlan>();
        }
        ArrayList<LocalPlan> nLocalPlans = new ArrayList<>(planArrayList);
        for(LocalPlan localPlan: planArrayList) {
            TimeConfig timeConfig = localPlan.getTimeConfig();
            int status;
            if(timeConfig.getWeeks()[0])
                continue;
            else if(!timeConfig.getWeeks()[DateTool.getWeek()])
                nLocalPlans.remove(localPlan);

        }
    }

    public int getUserId() {
        return sharedPreferences.getInt("userId", -1);
    }
}
