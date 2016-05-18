package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.MenuItem;
import imu.pcloud.app.R;

/**
 * Created by Administrator on 2016/5/18.
 */
public class UserSharingActivity extends HttpActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sharing_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
