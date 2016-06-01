package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import imu.pcloud.app.R;

/**
 * Created by Administrator on 2016/6/1.
 */
public class CheckSharingPlanActivity extends HttpActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_sharing_plan);
        Intent intent_accept = getIntent();
        Bundle bundle = intent_accept.getExtras();
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        String planName = bundle.getString("planName");
        setActionBar(planName);
    }

    @Override
    protected void onSuccess() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.comment_downloan, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
