package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.SharingRecord;
import imu.pcloud.app.model.PlanSharingListModel;
import imu.pcloud.app.utils.AdspterHide;
import imu.pcloud.app.utils.PlanCircleAdspter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/17.
 */
public class PlanCircleActivity extends HttpActivity{

    private ListView listView1;
    private List<Map<String, Object>> list;
    private List<SharingRecord> sharingRecords;
    private List<PersonalPlan> personalPlens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plancircle_layout);
        listView1 = find(R.id.plancircle_listView);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent_accept = getIntent();
        Bundle bundle = intent_accept.getExtras();
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        String planCirleName = bundle.getString("planCircleName");
        setActionBar(planCirleName);
        int planCircleID = bundle.getInt("planCircleID");
        get("getSharingList","planCircleId",planCircleID);
        //get()s
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
    protected void onSuccess() {
        PlanSharingListModel userSharingList = getObject(PlanSharingListModel.class);
        if(userSharingList.getStatus() == 400){
            personalPlens=userSharingList.getPersonalPlans();
            sharingRecords = userSharingList.getSharingRecords();
            list = getData();
            PlanCircleAdspter listAdapter = new PlanCircleAdspter(this,this,list);
//            ListAdapter listAdapter=new SimpleAdapter(this,list, R.layout.plancircle_item,
//                    new String[]{"name"}, new int[]{ R.id.plancircle_name});
            listView1.setAdapter(listAdapter);
        } else {
            toast("网络连接出现问题");
        }
    }
}
