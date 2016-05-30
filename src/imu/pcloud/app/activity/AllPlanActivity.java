package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import imu.pcloud.app.R;
import imu.pcloud.app.adapter.AllPlanAdapter;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.model.LocalPlan;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.model.PlanList;
import imu.pcloud.app.model.Plans;
import imu.pcloud.app.utils.PlanListTool;

import java.util.*;

/**
 * Created by acer on 2016/5/15.
 */
public class AllPlanActivity extends HttpActivity
        implements AdapterView.OnItemClickListener,
        PullToRefreshBase.OnRefreshListener<ListView>,AllPlanAdapter.MyOnClickListener {

    ArrayList<PersonalPlan> personalPlanArrayList = new ArrayList<PersonalPlan>();
    ArrayList<Integer> selectedPlanId = new ArrayList<Integer>();
    private PullToRefreshListView listView;
    AllPlanAdapter allPlanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allplan);
        init();
        allPlanAdapter.notifyDataSetChanged();
    }

    private void init() {
        setActionBar("设置计划");
        listView = (PullToRefreshListView) findViewById(R.id.allplan_listview);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
        listView.setOnRefreshListener(this);
        allPlanAdapter = new AllPlanAdapter(this, personalPlanArrayList, selectedPlanId);
        allPlanAdapter.setMyOnClickListener(this);
        listView.setAdapter(allPlanAdapter);
        setPlans();
        get("getPlanList", "cookies", getCookie());
    }

    @Override
    protected void onSuccess() {
        PlanList planList =  getObject(PlanList.class);
        if(planList.getStatus() != 0) {
            toast(planList.getResult());
        }
        else {
            toast("获取云端计划成功");
            mergeCloudPlanToLocal((ArrayList<PersonalPlan>) planList.getPersonalPlans());
        }
        listView.onRefreshComplete();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void setNowPlan() {
        ArrayList<LocalPlan> localPlans = new ArrayList<LocalPlan>();
        for(Integer selectedId:selectedPlanId) {
            for(PersonalPlan plan:personalPlanArrayList) {
                if(plan.getId() == selectedId) {
                    String content = plan.getContent();
                    Plans plans = new Plans();
                    plans.setByJsonString(content);
                    for(Plan p:plans.getPlans()) {
                        LocalPlan localPlan = new LocalPlan(p.getStartTimeString(), p.getEndTimeString(), p.getContent(), p.getTitle(), plan.getName());
                        localPlans.add(localPlan);
                    }
                }
            }
        }
        PlanListTool.sort(localPlans);
        editor.putString("nowPlan" + getUserId(), gson.toJson(localPlans));
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
        allPlanAdapter.notifyDataSetChanged();
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
        allPlanAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFailure() {
        super.onFailure();
        listView.onRefreshComplete();
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

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                get("getPlanList", "cookies", getCookie());
            }
        }, 1000);
    }

    @Override
    public void onClickItem(View view, int position) {
        PersonalPlan plan = personalPlanArrayList.get(position);
        if(view.getId() == R.id.item) {
            if (plan.getUserId() != getUserId()) {
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
        else {
            int i = 0;
            int length = selectedPlanId.size();
            for(i = 0; i < length; i++){
                int selectId = selectedPlanId.get(i);
                if(selectId == plan.getId()) {
                    view.setSelected(false);
                    selectedPlanId.remove(i);
                    break;
                }
            }
            if(i == length) {
                selectedPlanId.add(plan.getId());
                view.setSelected(true);
            }
        }
    }
    public void getData() {
        allPlanAdapter.getData();
    }
}
