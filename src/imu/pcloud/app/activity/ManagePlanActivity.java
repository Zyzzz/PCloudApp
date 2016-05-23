package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import imu.pcloud.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/5/23.
 */
public class ManagePlanActivity extends Activity {
    private ListView listView;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageplan_layout);
    }
}
