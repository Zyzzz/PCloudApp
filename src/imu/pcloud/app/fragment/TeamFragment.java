package imu.pcloud.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.utils.MyAdspter;

/**
 * Created by acer on 2016/5/11.
 */
public class TeamFragment extends HttpFragment {
    private ListView listview;
    MyAdspter myAdspter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initActionBar();
        listview= (ListView) getActivity().findViewById(R.id.teammember_list);
        listview.setAdapter(myAdspter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();


            }
        });
        return inflater.inflate(R.layout.team_layout, container, false);
    }

    private void initActionBar() {
        View actionbarLayout = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.actionbar_layout, null);
        TextView textview=(TextView) actionbarLayout.findViewById(R.id.acText);
        textview.setText(SPACE + "群计划");
        getActivity().getActionBar().setCustomView(actionbarLayout);
    }




    @Override
    protected void onSuccess() {

    }

    public void onHiddenChanged(boolean hidden) {
        if(hidden == false)
            initActionBar();
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        initActionBar();
        super.onResume();
    }
}
