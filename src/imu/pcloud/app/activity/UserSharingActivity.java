package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.SharingRecord;
import imu.pcloud.app.model.UserSharingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/18.
 */
public class UserSharingActivity extends HttpActivity{

    private ListView listView1;
    private List<Map<String, Object>> list;
    private List<SharingRecord> sharingRecords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sharing_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        listView1 = (ListView)findViewById(R.id.user_sharing_listView);
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
            Map<String, Object> map = new HashMap<String, Object>();
           // map.put("image", R.drawable.header);
            map.put("name",sharingRecords.get(i).getId());
            //map.put("id",planCircles.get(i).getId());
            // map.put("info", "这是一个详细信息" + i);
            list.add(map);
        }
        return list;
    }

    @Override
    protected void onSuccess() {
        UserSharingList userSharingList = getObject(UserSharingList.class);
        if(userSharingList.getStatus() == 303){
            sharingRecords = userSharingList.getSharingRecords();
            list = getData();
            ListAdapter listAdapter=new SimpleAdapter(this,list, R.layout.user_sharing_list_item,
                    new String[]{"name"}, new int[]{ R.id.user_sharing_name});
            listView1.setAdapter(listAdapter);
        } else {
            toast("得到用户列表失败，请重新登录");
        }
    }
}
