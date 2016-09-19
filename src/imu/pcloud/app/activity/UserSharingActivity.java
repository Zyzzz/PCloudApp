package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.SharingRecord;
import imu.pcloud.app.model.BaseModel;
import imu.pcloud.app.model.PlanSharingListModel;
import imu.pcloud.app.adapter.AdspterHide;

import java.util.*;

/**
 * Created by Administrator on 2016/5/18.
 */
public class UserSharingActivity extends HttpActivity implements PullToRefreshBase.OnRefreshListener<ListView>{

    private PullToRefreshListView listView1;
    private boolean flag;
    private List<Map<String, Object>> list;
    private List<SharingRecord> sharingRecords;
    private List<PersonalPlan> personalPlans;
    AdspterHide listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sharing_layout);
        flag = true;
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setActionBar(R.layout.actionbar_check_layout, "我的分享");
        listView1 = (PullToRefreshListView)findViewById(R.id.user_sharing_listView);
        listView1.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView1.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView1.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView1.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
        listView1.setOnRefreshListener(this);
        get("getUserSharingList","cookies",getCookie());
        setPlanCircles();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < sharingRecords.size(); i++) {
            for(int j = 0; j< personalPlans.size(); j++) {
                if(sharingRecords.get(i).getId().getPersonalPlanId()== personalPlans.get(j).getId()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    String circleName = "";
                    for(int m = 0; m < planCircles.size(); m++) {
                        if(planCircles.get(m).getId() == sharingRecords.get(i).getId().getPlanCircleId()) {
                            circleName = planCircles.get(m).getName();
                            break;
                        }
                    }
                    map.put("name", personalPlans.get(j).getName() + "(分享到：" + circleName + ")");
                    map.put("planId", personalPlans.get(j).getId());
                    list.add(map);
                }
            }
        }
        return list;
    }
    public void deleteUserSharing(int position){
        flag = false;
        Integer personalPlanId = sharingRecords.get(position).getId().getPersonalPlanId();
        Integer planCircleId = sharingRecords.get(position).getId().getPlanCircleId();
        get("deleteSharing","personalPlanId",personalPlanId,"planCircleId",planCircleId);
    }

    @Override
    protected void onFailure() {
        super.onFailure();
        if(listView1.isRefreshing())
            listView1.onRefreshComplete();
    }
    @Override
    protected void onSuccess() {
        if(flag) {
            PlanSharingListModel userSharingList = getObject(PlanSharingListModel.class);
            if (userSharingList.getStatus() == 400) {
                if (!userSharingList.getSharingRecords().isEmpty()) {
                    sharingRecords = userSharingList.getSharingRecords();
                    personalPlans = userSharingList.getPersonalPlans();
                    list = getData();
                    listAdapter = new AdspterHide(this, this, list, personalPlans);
                    listView1.setAdapter(listAdapter);
                }
                else {
                    toast("分享列表为空");
                }
            } else {
                toast("得到用户分享列表失败，请重新登录");
            }
        }else {
            BaseModel baseModel = getObject(BaseModel.class);
            if(baseModel.getStatus()==302) {
                toast(baseModel.getResult());
                listView1.setRefreshing();
            }else {
                toast("删除失败");
            }
            flag = true;
        }
        if(listView1.isRefreshing())
            listView1.onRefreshComplete();
    }
    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                get("getUserSharingList","cookies",getCookie());
            }
        }, 1000);
    }
}
