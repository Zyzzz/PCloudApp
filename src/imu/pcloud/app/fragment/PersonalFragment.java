package imu.pcloud.app.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.*;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.AllPlanActivity;
import imu.pcloud.app.adapter.PlanItemAdapter;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.model.LocalPlan;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.model.TimeConfig;
import imu.pcloud.app.utils.DateTool;
import imu.pcloud.app.utils.PushTool;
import imu.pcloud.app.utils.SpaceUtil;
import imu.pcloud.app.utils.WidgetController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/5/11.
 */
public class PersonalFragment extends HttpFragment {
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private ArrayList<LocalPlan> planArrayList = new ArrayList<LocalPlan>();
    private ListView listView;
    private ActionBar myActionBar;
    PlanItemAdapter listAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_manage:
                startActivity(AllPlanActivity.class);
                break;
//            case R.id.action_updatedays:
//                break;
        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_layout, container, false);
        listView = (ListView) view.findViewById(R.id.personal_listview);
        initNowPlan();
        setHasOptionsMenu(true);
        setActionBar();
        return view;
    }

    public void initNowPlan() {
        String nowPlanString = sharedPreferences.getString("nowPlan" + getUserId(), "");
        try {
            planArrayList = gson.fromJson(nowPlanString,
                    new TypeToken<ArrayList<LocalPlan>>() {
                    }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            planArrayList = new ArrayList<LocalPlan>();
            editor.remove("nowPlan" + getUserId());
            editor.commit();
        }
        if(planArrayList == null){
            planArrayList = new ArrayList<LocalPlan>();
        }
        ArrayList<LocalPlan> nLocalPlans = new ArrayList<>(planArrayList);
        for(LocalPlan localPlan: planArrayList) {
            TimeConfig timeConfig = localPlan.getTimeConfig();
            int status;
            if(timeConfig.getWeeks()[0])
                continue;
            else if(!timeConfig.getWeeks()[DateTool.getWeek()])
                nLocalPlans.remove(localPlan);

        }
        listAdapter = new PlanItemAdapter(nLocalPlans, getActivity());
        listView.setAdapter(listAdapter);
    }

    private void setActionBar() {
        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        myActionBar=getActivity().getActionBar();
        myActionBar.setDisplayHomeAsUpEnabled(false);
        // 左侧图标点击事件使能
        myActionBar.setHomeButtonEnabled(false);
        // 使左上角图标(系统)是否显示
        myActionBar.setDisplayShowHomeEnabled(false);
        //myActionBar.setTitle("");
        myActionBar.setIcon(R.drawable.return_icon);
        // 显示标题
        myActionBar.setDisplayShowTitleEnabled(false);
        myActionBar.setDisplayShowCustomEnabled(true);//显示自定义视图
        View actionbarLayout = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.actionbar_fra_layout, null);
        TextView textview=(TextView) actionbarLayout.findViewById(R.id.acText);
        textview.setText(SpaceUtil.getSpace(8) + "个人计划");
        myActionBar.setCustomView(actionbarLayout);
    }

    @Override
    public void onResume() {
        //setActionBar();
        initNowPlan();
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden == false) {
            //initNowPlan();
            setActionBar();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void onSuccess() {


    }


    public void getData(List<Map<String,Object>> pList) {
        pList.clear();
        for (int i = 0; i < planArrayList.size(); i++)
        {
            LocalPlan plan = planArrayList.get(i);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("start_time", plan.getStartTimeString());
            map.put("end_time", plan.getEndTimeString());
            map.put("content", plan.getContent());
            map.put("title", plan.getTitle() + "(来自:" + plan.getName() + ")");
            pList.add(map);
        }
    }

        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.personal, menu);
        return ;
    }


    public void initPushService() {
    }
}
