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
import imu.pcloud.app.listener.OnPlanInputListener;
import imu.pcloud.app.model.BaseModel;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.model.Plans;
import imu.pcloud.app.utils.PlanListTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyu on 2016/6/4.
 */
public class AddMultiPlanActivity extends HttpActivity implements AdapterView.OnItemClickListener, OnPlanInputListener, DialogInterface.OnClickListener{

    private ArrayList<Plan> planArrayList = new ArrayList<Plan>();
    private ListView listView;
    private EditText etPlanName;
    private EditText etMaxmumber;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private Plan newPlan= new Plan("", "", "点击添加新内容", "");
    private Plan nowPlan;
    private int maxmumber = 0;
    SimpleAdapter listAdapter;
    Plans plans = new Plans();
    PersonalPlan personalPlan = new PersonalPlan();
    String planName = "新多人计划";
    int addFlag = 0;
    int editFlag = 0;
    int planId = 0;

    public Plan getNowPlan() {
        return nowPlan;
    }

    public void setNowPlan(Plan nowPlan) {
        this.nowPlan = nowPlan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        if(data != null) {
            editFlag = 1;
            initEdit(data);
        }
        init();
    }

    private void initEdit(Bundle saveInstanceState) {
        Plans plans = new Plans();
        plans.setByJsonString(getIntent().getExtras().getString("planString", ""));
        planName = saveInstanceState.getString("planName", "新多人计划");
        planId = saveInstanceState.getInt("planId", -1);
        planArrayList = plans.getPlans();
        maxmumber = saveInstanceState.getInt("maxmumber", 0);
    }

    protected void init() {
        setActionBar("编辑群计划");
        setContentView(R.layout.add_plan_item_layout);
        listView = find(R.id.personal_listview);
        listAdapter=new SimpleAdapter(this, pList, R.layout.personal_list_item,
                new String[]{"start_time","end_time", "content", "title"}, new int[]{R.id.start_time, R.id.end_time, R.id.plan_content, R.id.plan_title});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        pushPlan();
    }

    public void pushPlan(Plan plan) {
        planArrayList.set(planArrayList.size() - 1, plan);
        planArrayList.add(newPlan);
        getData(pList);
        listAdapter.notifyDataSetChanged();
    }
    public void pushPlan() {
        planArrayList.add(newPlan);
        getData(pList);
        listAdapter.notifyDataSetChanged();
    }

    public void setPlan(Plan plan, int pos) {
        planArrayList.set(pos, plan);
        getData(pList);
        listAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onSuccess() {
        BaseModel result = getObject(BaseModel.class);
        if(result.getStatus() == 0) {
            if(editFlag == 0)
                toast("创建成功");
            else
                toast("修改成功");
            this.finish();
        }
        else {
            toast(result.getResult());
        }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == planArrayList.size() - 1) {
            showAddItemDialog();
        }
        else {
            showItemDialog(planArrayList.get(position), position);
        }
    }

    public void showAddItemDialog() {
        addFlag = -1;
        nowPlan = null;
        AddItemFragment addItemFragment = new AddItemFragment();
        addItemFragment.setOnPlanInputListener(this);
        addItemFragment.show(getFragmentManager(), "添加新计划");
    }

    public void showItemDialog(Plan plan, int pos) {
        addFlag = pos;
        nowPlan = plan;
        AddItemFragment addItemFragment = new AddItemFragment();
        addItemFragment.setOnPlanInputListener(this);;
        addItemFragment.setPlan(plan);
        addItemFragment.show(getFragmentManager(), "修改计划");
    }

    @Override
    public void onPlanInputed(Plan plan) {
        if(addFlag == -1)
            pushPlan(plan);
        else {
            setPlan(plan, addFlag);
        }
        sortPlan();
    }

    public void sortPlan() {
        planArrayList.remove(planArrayList.size() - 1);
        PlanListTool.sort(planArrayList);
        planArrayList.add(newPlan);
        getData(pList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.add_plan, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.confirm:
                if(planArrayList.size() < 2) {
                    toast("请至少输入一条计划");
                    return false;
                }
                ArrayList<Plan> pal = new ArrayList<Plan>(planArrayList);
                pal.remove(pal.size() - 1);
                plans.setPlans(pal);
                View view = getLayoutInflater().inflate(R.layout.set_multi_plan_info_layout, null);
                etPlanName = (EditText) view.findViewById(R.id.plan_name);
                etMaxmumber = (EditText) view.findViewById(R.id.max_munber);
                etPlanName.setText(planName);
                if(maxmumber != 0)
                    etMaxmumber.setText("" + maxmumber);
                new AlertDialog.Builder(this).setTitle("请输入计划信息").
                        setView(view).
                        setPositiveButton("确定", this).
                        setNegativeButton("取消", null).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onClick(DialogInterface dialog, int id) {
        if(editFlag == 0) {
            planName = etPlanName.getText().toString();
            maxmumber = Integer.parseInt(etMaxmumber.getText().toString());
            get("addMultiPlan", "content", plans.getJsonString(),
                    "name", planName,
                    "cookies", getCookie(),
                    "maxmumber", maxmumber,
                    "condition", 0);
        }
        else {
            planName = etPlanName.getText().toString();
            get("modfiyMultiPlan",
                    "content", plans.getJsonString(),
                    "name", planName,
                    "multiPlanId", planId,
                    "maxmumber", maxmumber);
        }
    }
}
