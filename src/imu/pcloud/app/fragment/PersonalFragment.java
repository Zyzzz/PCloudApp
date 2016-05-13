package imu.pcloud.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/5/11.
 */
public class PersonalFragment extends HttpFragment {
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private Image Image[]={};
    private  String PlanContent[]={"hello","see you"};
    private ListView listView1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       getData(pList);
        View view = inflater.inflate(R.layout.zone_layout, container, false);
        listView1 = (ListView) view.findViewById(R.id.zone_listView);
        ListAdapter listAdapter=new SimpleAdapter(this.getActivity(),pList,R.layout.personal_list_item,
                new String[]{"Image","Content"},new int[]{R.id.image,R.id.content});
        listView1 = (ListView) view.findViewById(R.id.personal_listview);
        listView1.setAdapter(listAdapter);
        return view;
    }

    @Override
    protected void OnSuccess() {

    }


    public void getData(List<Map<String,Object>> pList) {
        for (int i=0;i<PlanContent.length;i++)
        {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("Image",R.drawable.icon);
            map.put("Content",PlanContent[i]);
            pList.add(map);
        }


    }
}
