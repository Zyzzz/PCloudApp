package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by acer on 2016/6/1.
 */
public class TeamMemberActivity extends HttpActivity {

    ListView listView;
    SimpleAdapter adapter;
    ArrayList<HashMap<String, Object>> plist = new ArrayList<HashMap<String, Object>>();
    ArrayList<User> userArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onSuccess() {

    }

    private void init() {
        setActionBar("群计划成员");
        setContentView(R.layout.teammenber_layout);
        listView = find(R.id.teammember_list);
        adapter = new SimpleAdapter(this, plist, R.layout.teammember_list_item,
                new String[]{"name"}, new int[]{R.id.nick_name});
        listView.setAdapter(adapter);
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
}
