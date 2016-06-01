package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.SharingRecord;
import imu.pcloud.app.model.PlanSharingListModel;
import imu.pcloud.app.adapter.PlanCircleAdspter;

import java.util.*;

/**
 * Created by Administrator on 2016/5/17.
 */
public class PlanCircleActivity extends HttpActivity implements PullToRefreshBase.OnRefreshListener<ListView>{

    private PullToRefreshListView listView1;
    private List<Map<String, Object>> list;
    private List<SharingRecord> sharingRecords;
    private List<PersonalPlan> personalPlens;
    private int planCircleID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plancircle_layout);
        listView1 = (PullToRefreshListView)findViewById(R.id.plancircle_listView);
        listView1.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView1.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView1.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView1.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
        listView1.setOnRefreshListener(this);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),CheckSharingPlanActivity.class);
               // System.out.print("ddddddddddddd"+position);
                intent.putExtra("planName",personalPlens.get(position-1).getName());
                intent.putExtra("planContext",personalPlens.get(position-1).getContent());
                intent.putExtra("PlanId",personalPlens.get(position-1).getId());
                startActivity(intent);
            }
        });
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent_accept = getIntent();
        Bundle bundle = intent_accept.getExtras();
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        String planCirleName = bundle.getString("planCircleName");
        setActionBar(planCirleName);
        planCircleID = bundle.getInt("planCircleID");
        //listView1.setRefreshing();
        //get()s
        get("getSharingList","planCircleId",planCircleID);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private  List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0 ;i<personalPlens.size();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", personalPlens.get(i).getName());
            list.add(map);
        }
        return list;
    }

    @Override
    protected void onFailure() {
        super.onFailure();
        listView1.onRefreshComplete();
    }

    @Override
    protected void onSuccess() {
        PlanSharingListModel userSharingList = getObject(PlanSharingListModel.class);
        if(userSharingList.getStatus() == 400){
            personalPlens=userSharingList.getPersonalPlans();
            sharingRecords = userSharingList.getSharingRecords();
            list = getData();
            //PlanCircleAdspter listAdapter = new PlanCircleAdspter(this,this,list);
            ListAdapter listAdapter=new SimpleAdapter(this,list, R.layout.plancircle_item, new String[]{"name"}, new int[]{ R.id.plancircle_name});
            listView1.setAdapter(listAdapter);
        } else {
            toast("网络连接出现问题");
        }
        listView1.onRefreshComplete();
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                get("getSharingList","planCircleId",planCircleID);
            }
        }, 1000);
    }
}
