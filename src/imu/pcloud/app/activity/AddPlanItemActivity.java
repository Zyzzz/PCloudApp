package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import imu.pcloud.app.model.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyu on 2016/5/23.
 */
public class AddPlanItemActivity extends HttpActivity {

    private ArrayList<Plan> planArrayList = new ArrayList<Plan>();
    private ListView listView;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setActionBar("编辑计划");
    }

    @Override
    protected void onSuccess() {

    }

    public void getData(List<Map<String,Object>> pList) {
        for (int i = 0; i < planArrayList.size(); i++)
        {
            Plan plan = planArrayList.get(i);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("start_time", plan.getStartTimeString());
            map.put("end_time", plan.getEndTimeString());
            map.put("content", plan.getContent());
            pList.add(map);
        }
    }
}
