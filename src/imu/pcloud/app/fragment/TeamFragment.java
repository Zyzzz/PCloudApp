package imu.pcloud.app.fragment;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import imu.pcloud.app.R;

/**
 * Created by acer on 2016/5/11.
 */
public class TeamFragment extends HttpFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initActionBar();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.team,menu);
        super.onCreateOptionsMenu(menu, inflater);
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
