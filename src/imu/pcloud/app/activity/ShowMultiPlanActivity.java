package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.MultiPlan;
import imu.pcloud.app.model.BaseModel;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.model.Plans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyu on 2016/6/4.
 */
public class ShowMultiPlanActivity extends HttpActivity {

    private ArrayList<Plan> planArrayList = new ArrayList<>();
    private List<HashMap<String,Object>> pList =new ArrayList<HashMap<String, Object>>();
    private ListView listView;
    private SimpleAdapter adapter;
    private MultiPlan multiPlan;
    private int mode;
    private int clickId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_layout);
        Bundle data = getIntent().getExtras();
        mode = data.getInt("flag", 0);
        multiPlan = gson.fromJson(data.getString("plan", ""), MultiPlan.class);
        init();
    }

    private void init() {
        if(multiPlan == null)
            return;
        Plans plans = new Plans();
        plans.setByJsonString(multiPlan.getContent());
        planArrayList = plans.getPlans();
        setActionBar(multiPlan.getName());
        listView = find(R.id.personal_listview);
        adapter = new SimpleAdapter(this, pList, R.layout.personal_list_item,
                new String[]{"start_time","end_time", "content", "title"},
                new int[]{R.id.start_time, R.id.end_time, R.id.plan_content, R.id.plan_title});
        listView.setAdapter(adapter);
        updateData();
    }

    public void updateData() {
        pList.clear();
        for(Plan plan:planArrayList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("start_time", plan.getStartTimeString());
            map.put("end_time", plan.getEndTimeString());
            map.put("content", plan.getContent());
            map.put("title", plan.getTitle());
            pList.add(map);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSuccess() {
        switch (clickId) {
            case R.id.join:
                BaseModel result = getObject(BaseModel.class);
                if(result.getStatus() == 0) {
                    toast("加入成功");
                    finish();
                }
                else {
                    toast(result.getResult());
                }
        }
    }

    private void joinPlan() {
        get("joinMultiPlan", "cookies", getCookie(), "multiPlanId", multiPlan.getId());
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        clickId = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.join:
                joinPlan();
                break;
            case R.id.lookmember:
                lookMemeber();
                break;
            case R.id.updateplan:
                updatePlan();
                break;
//            case R.id.check_multi_plan:
//
//                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void lookMemeber() {
        startActivity(TeamMemberActivity.class, getIntent().getExtras());
    }

    public void updatePlan() {
        if (multiPlan.getUserId() != getUserId()) {
            toast("您没有修改计划的权限");
            return;
        }
        Bundle data = new Bundle();
        data.putString("multi_plan", gson.toJson(multiPlan));
        Intent intent = new Intent(this, AddMultiPlanActivity.class);
        intent.putExtras(data);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        switch(mode) {
            case 0:
                getMenuInflater().inflate(R.menu.team, menu);
                break;
            case 1:
                getMenuInflater().inflate(R.menu.overview_team, menu);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode) {
            case 0:
                if(data != null) {
                    multiPlan = gson.fromJson(data.getExtras().getString("multi_plan", ""), MultiPlan.class);
                    init();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
