package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import imu.pcloud.app.R;

/**
 * Created by Administrator on 2016/5/17.
 */
public class PlanCircleActivity extends HttpActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plancircle_layout);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent_accept = getIntent();
        Bundle bundle = intent_accept.getExtras();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        int planCircleID = bundle.getInt("planCircleID");
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSuccess() {

    }
}
