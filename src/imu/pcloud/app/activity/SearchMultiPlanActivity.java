package imu.pcloud.app.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import imu.pcloud.app.R;
import imu.pcloud.app.been.MultiPlan;
import imu.pcloud.app.model.MultiPlanList;
import imu.pcloud.app.model.SharingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyu on 2016/6/4.
 */
public class SearchMultiPlanActivity extends HttpActivity implements
        View.OnClickListener,
        AdapterView.OnItemClickListener, SimpleAdapter.ViewBinder {

    ListView listView;
    EditText text;
    View search;
    SimpleAdapter adapter;
    private ArrayList<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private MultiPlanList multiPlanList = new MultiPlanList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.search_multi_plan_layout);
        setActionBar(R.layout.actionbar_check_layout, "加入群计划");
        text = find(R.id.search_text);
        text.clearFocus();
        search = find(R.id.search_button);
        listView = find(R.id.multi_listview);
        adapter = new SimpleAdapter(this, pList, R.layout.plancircle_item,
                new String[]{"name", "image"}, new int[]{R.id.plancircle_name, R.id.plancircle_image});
        adapter.setViewBinder(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    protected void onSuccess() {
        MultiPlanList result = getObject(MultiPlanList.class);
        if(result.getStatus() == 0) {
            multiPlanList = result;
            updateData();
        }
    }

    public void updateData() {
        pList.clear();
        for(MultiPlan multiPlan: multiPlanList.getMultiPlans()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", multiPlan.getName());
            map.put("image", imageUtil.getIcon(multiPlan.getId()));
            pList.add(map);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.search_button:
                search();
                break;
        }
    }

    @Override
    protected void onFailure() {
        super.onFailure();
    }

    public void search() {
        String str = text.getText().toString();
        get("getMultiPlanListByBlurryName", "blurryName", str);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MultiPlan multiPlan = multiPlanList.getMultiPlans().get(position);
        Bundle data = new Bundle();
        data.putString("plan", gson.toJson(multiPlan));
        data.putInt("flag", 1);
        startActivity(ShowMultiPlanActivity.class, data);
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
}
