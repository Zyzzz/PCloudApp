package imu.pcloud.app.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import imu.pcloud.app.service.PushMsgService;

/**
 * Created by guyu on 2016/6/10.
 */
public class MyReciver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, PushMsgService.class));
    }
}
