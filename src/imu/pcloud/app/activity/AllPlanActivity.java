package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/5/15.
 */
public class AllPlanActivity extends HttpActivity {

    ArrayList<PersonalPlan> personalPlanArrayList = new ArrayList<>();
    private ListView listView;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    SimpleAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allplan);

        init();
    }

    private void init() {
        setActionBar("设置计划");
    }

    @Override
    protected void onSuccess() {

    }

}
