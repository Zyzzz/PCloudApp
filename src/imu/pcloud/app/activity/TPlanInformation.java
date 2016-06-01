package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import imu.pcloud.app.R;

/**
 * Created by acer on 2016/6/1.
 */
public class TPlanInformation extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tplancontent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.team, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
