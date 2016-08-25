package imu.pcloud.app.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.WelcomeActivity;
import imu.pcloud.app.model.LocalPlan;
import imu.pcloud.app.model.TimeConfig;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by guyu on 2016/6/10.
 */
public class PushTool {

    NotificationManager notificationManager;
    ArrayList<LocalPlan> localPlanArrayList = new ArrayList<>();
    public final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_TIME_TICK)) {
                int i = 0;
                for(LocalPlan plan: localPlanArrayList) {
                    if(hasCall(plan))
                        push(context, plan, i);
                    i++;
                }
            }
        }
    };

    public PushTool() {
    }


    public void push(Context context, LocalPlan plan, int pos) {
        String title =  plan.getTitle() + "(来自:" + plan.getName() + ")";
        Notification notification = new Notification(
                R.drawable.logo, title, System.currentTimeMillis());
        Intent intent = new Intent(context, WelcomeActivity.class);
        PendingIntent pi= PendingIntent.getActivity(context, 0, intent, 0);
        notification.setLatestEventInfo(context, title, plan.getContent(), pi);
        notificationManager.notify(1, notification);
    }

    public ArrayList<LocalPlan> getLocalPlanArrayList() {
        return localPlanArrayList;
    }

    public void setLocalPlanArrayList(ArrayList<LocalPlan> localPlanArrayList) {
        this.localPlanArrayList = localPlanArrayList;
    }

    public boolean hasCall(LocalPlan plan) {
        Date date = new Date();
        Time now = new Time(date.getTime());
        now.setSeconds(0);
        Time start = DateTool.stringToTime(plan.getStartTimeString());
        start.setSeconds(0);
        if(now.getHours() == start.getHours() && now.getMinutes() == start.getMinutes())
            return true;
        return false;
    }
}
