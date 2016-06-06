package imu.pcloud.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.AccountActivity;
import imu.pcloud.app.activity.InformationActivity;
import imu.pcloud.app.activity.PlanCircleActivity;
import imu.pcloud.app.activity.UserSharingActivity;
import imu.pcloud.app.model.UserModel;
import imu.pcloud.app.utils.Information;

/**
 * Created by acer on 2016/5/11.
 */
public class SettingFragment extends HttpFragment implements View.OnClickListener{
    View head;
    View mySharing;
    View myAccount;
    ImageView header;
    TextView nickName;
    TextView email;
    UserModel userModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userModel = getUserModel();
        View view = inflater.inflate(R.layout.individual_activity, container, false);
        head = view.findViewById(R.id.personal_info);
        mySharing = view.findViewById(R.id.my_sharing);
        myAccount = view.findViewById(R.id.my_account);
        header = (ImageView) view.findViewById(R.id.personal_header);
        header.setBackgroundDrawable(imageUtil.getHeader(getUserId()));
        head.setOnClickListener(this);
        mySharing.setOnClickListener(this);
        myAccount.setOnClickListener(this);
        nickName = (TextView) view.findViewById(R.id.nick_name);
        email = (TextView) view.findViewById(R.id.email);
        nickName.setText(userModel.getUsername());
        email.setText(userModel.getEmail());
        initActionBar();
        return view;
    }

    @Override
    protected void onSuccess() {

    }

    @Override
    public void onResume() {
        //initActionBar();
        userModel = getUserModel();
        nickName.setText(userModel.getUsername());
        email.setText(userModel.getEmail());
        super.onResume();
    }

    private void initActionBar() {
        View actionbarLayout = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.actionbar_fra_layout, null);
        TextView textview=(TextView) actionbarLayout.findViewById(R.id.acText);
        textview.setText("我");
        getActivity().getActionBar().setCustomView(actionbarLayout);
    }

    public void onHiddenChanged(boolean hidden) {
        if(hidden == false)
            initActionBar();
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_info:
                startActivity(InformationActivity.class);
                break;
            case R.id.my_sharing:
                startActivity(UserSharingActivity.class);
                break;
            case R.id.my_account:
                startActivity(AccountActivity.class);
                break;
        }
    }
}
