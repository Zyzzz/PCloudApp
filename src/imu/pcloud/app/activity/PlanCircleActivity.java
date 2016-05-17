package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/5/17.
 */
public class PlanCircleActivity extends HttpActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent_accept = getIntent();
        Bundle bundle = intent_accept.getExtras();
        int planCircleID = bundle.getInt("planCircleID");
    }

    @Override
    protected void onSuccess() {

    }
}
