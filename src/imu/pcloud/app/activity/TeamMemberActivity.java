package imu.pcloud.app.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.MultiPlan;
import imu.pcloud.app.been.MultiPlanMember;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.User;
import imu.pcloud.app.model.ImageModel;
import imu.pcloud.app.model.MultiPlanList;
import imu.pcloud.app.utils.ImageUtil;
import imu.pcloud.app.utils.ViewFinder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by acer on 2016/6/1.
 */
public class TeamMemberActivity extends HttpActivity implements SimpleAdapter.ViewBinder, ImageUtil.OnLoadListener {

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
        imageUtil.setOnLoadListener(this);
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
        setActionBar(R.layout.actionbar_check_layout, "群计划成员");
        setContentView(R.layout.teammenber_layout);
        listView = find(R.id.teammember_list);
        adapter = new SimpleAdapter(this, plist, R.layout.teammember_list_item,
                new String[]{"name", "image"}, new int[]{R.id.nick_name, R.id.header});
        listView.setAdapter(adapter);
        adapter.setViewBinder(this);
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
            if(u.getHeadImageId() == null)
                u.setHeadImageId(0);
            map.put("image", imageUtil.getHeader(u.getId(), u.getHeadImageId()));
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

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        if(view instanceof ImageView && data instanceof Drawable) {
            ImageView iv = (ImageView) view;
            iv.setImageDrawable((Drawable) data);
            return true;
        }
        return false;
    }

    @Override
    public void onLoad(ImageModel imageModel) {
        updateData();
    }
}
