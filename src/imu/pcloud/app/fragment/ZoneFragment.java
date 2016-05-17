package imu.pcloud.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private List<Map<String, Object>> list;
    MyAdspter myAdspter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zone_layout, container, false);
        listView1 = (ListView) view.findViewById(R.id.zone_listView);
        get("getPlanCircleList");
        return view;
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < planCircles.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.drawable.ic_launcher);
            map.put("name",planCircles.get(i).getName());
            map.put("id",planCircles.get(i).getId());
            // map.put("info", "这是一个详细信息" + i);
            list.add(map);
        }
        return list;
    }




    @Override
    protected void onSuccess() {
        PlanCircleList planCircleList = getObject(PlanCircleList.class);
        if(planCircleList.getStatus() ==0) {
            planCircles = planCircleList.getPlanCircles();
            list = getData();
            myAdspter = new MyAdspter(getActivity().getApplicationContext(), list);
            listView1.setAdapter(myAdspter);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity().getApplicationContext(),PlanCircle.class);
                    intent.putExtra("planCircleID",(int)list.get(position).get("id"));
                    startActivity(intent);
                }
            });
        }
    }
}
