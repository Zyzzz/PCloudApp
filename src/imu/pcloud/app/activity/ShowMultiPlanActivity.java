package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.MultiPlan;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_layout);
        init();
    }

    private void init() {
        Bundle data = getIntent().getExtras();
        mode = data.getInt("flag", 0);
        multiPlan = gson.fromJson(data.getString("plan", ""), MultiPlan.class);
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

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }


}
