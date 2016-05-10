package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import imu.pcloud.app.R;

/**
 * Created by guyu on 2016/5/9.
 */
public class TestActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.test_layout);
    }
}