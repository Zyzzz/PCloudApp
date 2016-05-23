package imu.pcloud.app.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.ManagePlanActivity;
import imu.pcloud.app.been.Image;
import imu.pcloud.app.model.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/5/11.
 */
public class PersonalFragment extends HttpFragment {
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private Plan plan = new Plan("7:25", "9:25", "我要好好听课", "上课");
    private ArrayList<Plan> planArrayList = new ArrayList<Plan>();
    private ListView listView;
    private ActionBar myActionBar;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_manage:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ManagePlanActivity.class);
                startActivity(intent); 
                return true;

            case R.id.action_updatedays:
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       for(int i = 0; i < 10; i++)
        planArrayList.add(plan);
        getData(pList);
        View view = inflater.inflate(R.layout.personal_layout, container, false);
        listView = (ListView) view.findViewById(R.id.personal_listview);
        ListAdapter listAdapter=new SimpleAdapter(this.getActivity(), pList, R.layout.personal_list_item,
                new String[]{"start_time","end_time", "content"}, new int[]{R.id.start_time, R.id.end_time, R.id.plan_content});
        listView.setAdapter(listAdapter);
        setHasOptionsMenu(true);
        setActionBar();
        return view;
    }





    private void setActionBar() {
        myActionBar=getActivity().getActionBar();
        myActionBar.setDisplayShowTitleEnabled(true);
        // 返回箭头（默认不显示）
        myActionBar.setDisplayHomeAsUpEnabled(false);
        // 左侧图标点击事件使能
        myActionBar.setHomeButtonEnabled(true);
        // 使左上角图标(系统)是否显示
        myActionBar.setDisplayShowHomeEnabled(false);
        // 显示标题
        myActionBar.setDisplayShowTitleEnabled(false);
        myActionBar.setDisplayShowCustomEnabled(true);//显示自定义视图
        View actionbarLayout = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.actionbar_layout, null);
        TextView textview=(TextView) actionbarLayout.findViewById(R.id.acText);
        textview.setText(SPACE + "个人计划");
        myActionBar.setCustomView(actionbarLayout);
    }

    @Override
    public void onResume() {
        setActionBar();
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden == false)
            setActionBar();
        super.onHiddenChanged(hidden);
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
