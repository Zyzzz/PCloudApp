package imu.pcloud.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PlanCircle;
import imu.pcloud.app.model.PlanCircleList;
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
    private List<PlanCircle> planCircles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zone_layout, container, false);
        get("getPlanCircleList");
        listView1 = (ListView) view.findViewById(R.id.zone_listView);
        List<Map<String, Object>> list = getData();
        MyAdspter myAdspter = new MyAdspter(inflater.getContext(), list);
        listView1.setAdapter(myAdspter);
        return view;
    }
    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < planCircles.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.drawable.ic_launcher);
            map.put("name",planCircles.get(i).getName());
           // map.put("info", "这是一个详细信息" + i);
            list.add(map);
        }
        return list;
    }

    @Override
    protected void OnSuccess() {
        PlanCircleList planCircleList = getObject(PlanCircleList.class);
        if(planCircleList.getStatus() ==0) {
            planCircles = planCircleList.getPlanCircles();
        }
    }
}
