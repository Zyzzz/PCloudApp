package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import imu.pcloud.app.R;

/**
 * Created by acer on 2016/5/23.
 */
public class ManagePlanActivity extends Activity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageplan_layout);
    }
}
