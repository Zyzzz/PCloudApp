package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.model.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyu on 2016/5/23.
 */
public class AddPlanItemActivity extends HttpActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Plan> planArrayList = new ArrayList<Plan>();
    private ListView listView;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private Plan newPlan= new Plan("", "", "New", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setActionBar("编辑计划");
        setContentView(R.layout.add_plan_item_layout);
        listView = find(R.id.personal_listview);
        pushPlan();
    }

    public void pushPlan(Plan plan) {
        planArrayList.set(planArrayList.size() - 1, plan);
        planArrayList.add(newPlan); getData(pList);
        ListAdapter listAdapter=new SimpleAdapter(this, pList, R.layout.personal_list_item,
                new String[]{"start_time","end_time", "content"}, new int[]{R.id.start_time, R.id.end_time, R.id.plan_content});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }
    public void pushPlan() {
        planArrayList.add(newPlan); getData(pList);
        ListAdapter listAdapter=new SimpleAdapter(this, pList, R.layout.personal_list_item,
                new String[]{"start_time","end_time", "content"}, new int[]{R.id.start_time, R.id.end_time, R.id.plan_content});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
