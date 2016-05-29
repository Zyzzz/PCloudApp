package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.model.PlanList;
import imu.pcloud.app.utils.RefreshableView;

import java.util.*;

/**
 * Created by acer on 2016/5/15.
 */
public class AllPlanActivity extends HttpActivity implements AdapterView.OnItemClickListener, RefreshableView.PullToRefreshListener
{

    ArrayList<PersonalPlan> personalPlanArrayList = new ArrayList<PersonalPlan>();
    private ListView listView;
    private RefreshableView refreshableView;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    SimpleAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allplan);
        init();
        listAdapter.notifyDataSetChanged();
    }

    private void init() {
        setActionBar("设置计划");
        refreshableView = (RefreshableView) findViewById(R.id.refreshview);
        refreshableView.setOnRefreshListener(this, 0);
        listView = find(R.id.allplan_listview);
        listAdapter=new SimpleAdapter(this, pList, R.layout.allplan_list_item,
                new String[]{"plan_name"}, new int[]{R.id.plan_name});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        setPlans();
        get("getPlanList", "cookies", getCookie());
        getData(pList);
    }

    @Override
    protected void onSuccess() {
        PlanList planList =  getObject(PlanList.class);
        if(planList.getStatus() != 0) {
            toast("获取云端计划失败");
        }
        else {
            toast("获取云端计划成功");
            mergeCloudPlanToLocal((ArrayList<PersonalPlan>) planList.getPersonalPlans());
        }
        refreshableView.finishRefreshing();
    }

    public void getData(List<Map<String,Object>> pList) {
        pList.clear();
        for (PersonalPlan plan:personalPlanArrayList)
        {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("plan_name", plan.getName());
            pList.add(map);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonalPlan plan = personalPlanArrayList.get(position);
        if(plan.getUserId() != getUserId()) {
            toast("不可以修改他人的计划");
            return;
        }
        String planString = plan.getContent();
        Bundle data = new Bundle();
        data.putString("planString", planString);
        data.putInt("status", 1);
        data.putString("planName", plan.getName());
        data.putInt("planId", plan.getId());
        startActivity(AddPlanItemActivity.class, data);
    }

    public void putPlans() {
        String plansString = gson.toJson(personalPlanArrayList);
        editor.putString("plansString" + getUserId(), plansString);
        editor.commit();
    }

    public void setPlans() {
        ArrayList<PersonalPlan> personalPlanArrayList = gson.fromJson(
                sharedPreferences.getString("plansString" + getUserId(), ""),
                new TypeToken<ArrayList<PersonalPlan>>() {
        }.getType());
        mergeCloudPlanToLocal(personalPlanArrayList);
    }

    public void putPlan(PersonalPlan plan) {
        setPlans();
        personalPlanArrayList.add(plan);
        putPlans();
    }

    public void setPlan(PersonalPlan plan, int index) {
        personalPlanArrayList.set(index, plan);
        putPlans();
    }

    @Override
    protected void onResume() {
        listAdapter.notifyDataSetChanged();
        get("getPlanList", "cookies", getCookie());
        super.onResume();
    }

    protected void mergeCloudPlanToLocal(ArrayList<PersonalPlan> cloud) {
        if(cloud == null)
            return;
        for (PersonalPlan plan: personalPlanArrayList) {
           if(plan.getUserId() != getUserId())
               cloud.add(plan);
        }
        personalPlanArrayList.clear();
        personalPlanArrayList.addAll(cloud);
        putPlans();
        getData(pList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                get("getPlanList", "cookies", getCookie());
            }
        }, 800);
    }

    @Override
    protected void onFailure() {
        super.onFailure();
        refreshableView.finishRefreshing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.set_plan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_plan:
                startActivity(AddPlanItemActivity.class);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
