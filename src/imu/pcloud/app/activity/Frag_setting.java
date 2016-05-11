package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import imu.pcloud.app.R;

/**
 * Created by acer on 2016/5/11.
 */
public class Frag_setting extends HttpFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_layout,container,false);
    }

    @Override
    protected void OnSuccess() {

    }
}
