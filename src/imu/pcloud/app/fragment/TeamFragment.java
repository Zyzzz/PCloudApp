package imu.pcloud.app.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.AddMultiPlanActivity;
import imu.pcloud.app.activity.SearchMultiPlanActivity;
import imu.pcloud.app.activity.ShowMultiPlanActivity;
import imu.pcloud.app.adapter.MyAdspter;
import imu.pcloud.app.been.MultiPlan;
import imu.pcloud.app.model.MultiPlanList;
import imu.pcloud.app.utils.SpaceUtil;

import java.util.*;

/**
 * Created by acer on 2016/5/11.
 */
public class TeamFragment extends HttpFragment implements
        PullToRefreshBase.OnRefreshListener,
        AdapterView.OnItemClickListener, SimpleAdapter.ViewBinder{
    private PullToRefreshListView listview;
    SimpleAdapter adapter;
    private ArrayList<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private MultiPlanList multiPlanList = new MultiPlanList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initActionBar();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.allplan, container, false);
        listview= (PullToRefreshListView) view.findViewById(R.id.allplan_listview);
        adapter = new SimpleAdapter(getActivity(), pList, R.layout.plancircle_item,
                new String[]{"name", "image"}, new int[]{R.id.plancircle_name, R.id.plancircle_image});
        listview.setAdapter(adapter);
        adapter.setViewBinder(this);
        listview.setOnRefreshListener(this);
        listview.setOnItemClickListener(this);
        refreshData();
        return view;
    }

    private void updateData() {
        pList.clear();
        for(MultiPlan multiPlan: multiPlanList.getMultiPlans()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", multiPlan.getName());
            map.put("image", imageUtil.getIcon(multiPlan.getId()));
            pList.add(map);
        }
        adapter.notifyDataSetChanged();
    }

    private void refreshData() {
        get("getMultiPlanListByUserId", "cookies", getCookie());
    }

    private void initActionBar() {
        View actionbarLayout = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.actionbar_fra_layout, null);
        TextView textview=(TextView) actionbarLayout.findViewById(R.id.acText);
        textview.setText(SpaceUtil.getSpace(8) + "群计划");
        getActivity().getActionBar().setCustomView(actionbarLayout);
    }

    @Override
    protected void onSuccess() {
        MultiPlanList result = getObject(MultiPlanList.class);
        if(result.getStatus() == 0) {
            multiPlanList = result;
            updateData();
            if(listview.isRefreshing())
                listview.onRefreshComplete();
        }
        else {
            toast(result.getResult());
            if(listview.isRefreshing())
                listview.onRefreshComplete();
        }
    }

    public void onHiddenChanged(boolean hidden) {
        if(hidden == false) {
            initActionBar();
            refreshData();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        //initActionBar();
        refreshData();
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.multi_plan, menu);
        return ;
    }

    @Override
    protected void onFailure() {
        super.onFailure();
        if(listview.isRefreshing())
            listview.onRefreshComplete();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_multi_plan:
                startActivity(AddMultiPlanActivity.class);
                break;
            case R.id.join_multi_plan:
                startActivity(SearchMultiPlanActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                refreshData();
            }
        }, 1000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MultiPlan multiPlan = multiPlanList.getMultiPlans().get(position - 1);
        Bundle data = new Bundle();
        data.putString("plan", gson.toJson(multiPlan));
        data.putInt("flag", 0);
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
