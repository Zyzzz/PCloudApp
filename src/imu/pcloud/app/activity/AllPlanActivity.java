package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.model.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/5/15.
 */
public class AllPlanActivity extends HttpActivity implements AdapterView.OnItemClickListener
{

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
        setPlans();
        getData(pList);
        listView = find(R.id.allplan_listview);
        listAdapter=new SimpleAdapter(this, pList, R.layout.personal_list_item,
                new String[]{"plan_name"}, new int[]{R.id.plan_name});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onSuccess() {

    }

    public void getData(List<Map<String,Object>> pList) {
        pList.clear();
        for (int i = 0; i < personalPlanArrayList.size(); i++)
        {
            PersonalPlan plan = personalPlanArrayList.get(i);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("plan_name", plan.getName());
            pList.add(map);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void putPlans() {
        String plansString = gson.toJson(personalPlanArrayList);
        editor.putString("plansString" + getUserId(), plansString);
        editor.commit();
    }

    public void setPlans() {
        personalPlanArrayList = gson.fromJson(
                sharedPreferences.getString("plansString" + getUserId(), ""),
                new TypeToken<ArrayList<PersonalPlan>>() {
        }.getType());
    }

    public void putPlan(PersonalPlan plan) {
        setPlans();
        personalPlanArrayList.add(plan);
        putPlans();
    }
}
