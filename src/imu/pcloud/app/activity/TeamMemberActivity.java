package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.MultiPlan;
import imu.pcloud.app.been.MultiPlanMember;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.User;
import imu.pcloud.app.model.MultiPlanList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by acer on 2016/6/1.
 */
public class TeamMemberActivity extends HttpActivity {

    ListView listView;
    SimpleAdapter adapter;
    ArrayList<HashMap<String, Object>> plist = new ArrayList<HashMap<String, Object>>();
    ArrayList<User> userArrayList = new ArrayList<User>();
    MultiPlan multiPlan;
    int mode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        refreshData();
    }

    @Override
    protected void onSuccess() {
        MultiPlanList result = getObject(MultiPlanList.class);
        userArrayList.clear();
        for(MultiPlanMember member: result.getMultiPlanMembers()) {
            userArrayList.add(member.getUser());
        }
        updateData();
    }

    private void init() {
        setActionBar("群计划成员");
        setContentView(R.layout.teammenber_layout);
        listView = find(R.id.teammember_list);
        adapter = new SimpleAdapter(this, plist, R.layout.teammember_list_item,
                new String[]{"name"}, new int[]{R.id.nick_name});
        listView.setAdapter(adapter);
        Bundle data = getIntent().getExtras();
        mode = data.getInt("flag", 0);
        multiPlan = gson.fromJson(data.getString("plan", ""), MultiPlan.class);
    }

    private void refreshData() {
        get("getMultiPlanMembers", "multiPlanId", multiPlan.getId());
    }

    private void updateData() {
        if(userArrayList == null)
            return;
        plist.clear();
        for(User u:userArrayList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", u.getUsername());
            plist.add(map);
        }
        adapter.notifyDataSetChanged();
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
