package imu.pcloud.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import imu.pcloud.app.R;

/**
 * Created by acer on 2016/5/11.
 */
public class ZoneFragment extends HttpFragment {

    private ListView listView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView1=find(R.id.listView);
        //listView1.setAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.zone_layout,container,false);
    }

    @Override
    protected void OnSuccess() {

    }
}
