package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/6/1.
 */
public class CheckSharingPlanActivity extends HttpActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent_accept = getIntent();
        Bundle bundle = intent_accept.getExtras();
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        String planName = bundle.getString("planName");
        setActionBar(planName);

    }

    @Override
    protected void onSuccess() {

    }
}
