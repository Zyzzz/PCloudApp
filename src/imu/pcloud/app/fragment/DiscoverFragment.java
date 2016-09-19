package imu.pcloud.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.DiscoverPlanAcitivity;
import imu.pcloud.app.adapter.DiscoverAdapter;
import imu.pcloud.app.been.Discover;
import imu.pcloud.app.model.DiscoverModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guyu on 2016/6/10.
 */
public class DiscoverFragment extends HttpFragment implements PullToRefreshBase.OnRefreshListener, DiscoverAdapter.OnItemClickListener {

    PullToRefreshListView listView;
    DiscoverAdapter discoverAdapter;
    ArrayList<Discover> discoverArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_activity, container, false);
        listView = (PullToRefreshListView) view.findViewById(R.id.discover_list);
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
        listView.setOnRefreshListener(this);
        initActionBar();
        loadData();
        return view;
    }

    public void loadData() {
        get("discover");
    }

    @Override
    protected void onSuccess() {
        try {
            DiscoverModel result = gson.fromJson(jsonString, DiscoverModel.class);
            discoverArrayList = result.getDiscoverList();
            if(discoverAdapter == null) {
                discoverAdapter = new DiscoverAdapter(getActivity(), discoverArrayList);
                listView.setAdapter(discoverAdapter);
                discoverAdapter.notifyDataSetChanged();
                discoverAdapter.setOnItemClickListener(this);
            } else {
                discoverAdapter.setDiscoverArrayList(discoverArrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(listView.isRefreshing())
                listView.onRefreshComplete();
        }
    }

    @Override
    protected void onFailure() {
        if(listView.isRefreshing())
            listView.onRefreshComplete();
    }

    private void initActionBar() {
        View actionbarLayout = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.actionbar_fra_layout, null);
        TextView textview=(TextView) actionbarLayout.findViewById(R.id.acText);
        textview.setText("计划圈");
        getActivity().getActionBar().setCustomView(actionbarLayout);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                loadData();
            }
        }, 1000);
    }

    @Override
    public void onItemClick(int position, View v) {
        switch(v.getId()) {
            case R.id.comtent_view:
                Bundle data = new Bundle();
                data.putString("discover", gson.toJson(discoverArrayList.get(position)));
                startActivity(DiscoverPlanAcitivity.class, data);
                break;
        }
    }

    public void onHiddenChanged(boolean hidden) {
        if(hidden == false) {
            initActionBar();
            loadData();
        }
        super.onHiddenChanged(hidden);
    }
}
