package imu.pcloud.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.utils.MyAdspter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/5/11.
 */
public class ZoneFragment extends HttpFragment {

    private ListView listView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //listView1.setAdapter();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        List<Map<String, Object>> list=getData();
        listView1=find(R.id.listView);
        listView1.setAdapter(new MyAdspter(getActivity().getApplicationContext(),list));
        return inflater.inflate(R.layout.zone_layout,container,false);
    }
    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.ic_launcher);
            map.put("title", "这是一个标题"+i);
            map.put("info", "这是一个详细信息"+i);
            list.add(map);
        }
        return list;
    }
    @Override
    protected void OnSuccess() {

    }
}
