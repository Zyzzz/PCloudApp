package imu.pcloud.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.fragment.AddItemFragment;
import imu.pcloud.app.model.BaseModel;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.model.Plans;
import imu.pcloud.app.utils.PlanListTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyu on 2016/9/18.
 */
public class ShowPlanActivity extends HttpActivity{

    private ArrayList<Plan> planArrayList = new ArrayList<Plan>();
    private ListView listView;
    private EditText text;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    SimpleAdapter listAdapter;
    PersonalPlan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        if(data != null) {
            initEdit(data);
            init();
        }
    }

    private void initEdit(Bundle saveInstanceState) {
        plan = gson.fromJson(saveInstanceState.getString("planString", ""), PersonalPlan.class);
        Plans plans = new Plans();
        plans.setByJsonString(plan.getContent());
        planArrayList = plans.getPlans();
        getData(pList);
    }

    protected void init() {
        setActionBar(plan.getName());
        setContentView(R.layout.add_plan_item_layout);
        listView = find(R.id.personal_listview);
        listAdapter=new SimpleAdapter(this, pList, R.layout.personal_list_item,
                new String[]{"start_time","end_time", "content", "title"}, new int[]{R.id.start_time, R.id.end_time, R.id.plan_content, R.id.plan_title});
        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onSuccess() {

    }

    public void getData(List<Map<String,Object>> pList) {
        pList.clear();
        for (int i = 0; i < planArrayList.size(); i++)
        {
            Plan plan = planArrayList.get(i);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("start_time", plan.getStartTimeString());
            map.put("end_time", plan.getEndTimeString());
            map.put("content", plan.getContent());
            map.put("title", plan.getTitle());
            pList.add(map);
        }
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
