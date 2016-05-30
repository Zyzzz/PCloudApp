package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.SharingRecord;
import imu.pcloud.app.model.PlanSharingListModel;
import imu.pcloud.app.utils.AdspterHide;
import imu.pcloud.app.utils.SlideListView;

import java.util.*;

/**
 * Created by Administrator on 2016/5/18.
 */
public class UserSharingActivity extends HttpActivity implements PullToRefreshBase.OnRefreshListener<ListView>{

    private PullToRefreshListView listView1;
    private List<Map<String, Object>> list;
    private List<SharingRecord> sharingRecords;
    private List<PersonalPlan> personalPlens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sharing_layout);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setActionBar("我的分享");
        listView1 = (PullToRefreshListView)findViewById(R.id.user_sharing_listView);
        listView1.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView1.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView1.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView1.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
        listView1.setOnRefreshListener(this);
        //listView1.initSlideMode(2);
//      listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                toast("aaaaaaaaaaaa");
//            }
//        });
        get("getUserSharingList","cookies",getCookie());
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
            for(int j = 0;j<personalPlens.size();j++) {
                if(sharingRecords.get(i).getId().getPersonalPlanId()==personalPlens.get(j).getId()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", personalPlens.get(j).getName());
                    list.add(map);
                }
            }
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
            sharingRecords = userSharingList.getSharingRecords();
            personalPlens = userSharingList.getPersonalPlans();
            list = getData();
           AdspterHide listAdapter = new AdspterHide(this,this,list);
//            ListAdapter listAdapter=new SimpleAdapter(this,list, R.layout.user_sharing_list_item,
//                    new String[]{"name"}, new int[]{ R.id.user_sharing_name});
            listView1.setAdapter(listAdapter);
        } else {
            toast("得到用户分享列表失败，请重新登录");
        }
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
