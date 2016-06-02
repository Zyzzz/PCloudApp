package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;

/**
 * Created by guyu on 2016/5/27.
 */
public class InformationActivity extends HttpActivity implements View.OnClickListener{
    private View nickname;
    private View sex;
    private View birthday;
    private View sign;
    private View edu;
    private View work;
    private TextView tvNickname;
    private TextView tvSex;
    private TextView tvEmail;
    private TextView tvBirthday;
    private TextView tvSign;
    private TextView tvEdu;
    private TextView tvWork;
    UserModel userModel;
    String layoutName;

    public TextView getTvNickname() {
        return tvNickname;
    }

    public void setTvNickname(TextView tvNickname) {
        this.tvNickname = tvNickname;
    }

    public TextView getTvSex() {
        return tvSex;
    }

    public void setTvSex(TextView tvSex) {
        this.tvSex = tvSex;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_activity);
        init();
    }

    @Override
    protected void onSuccess() {
        UserModel result = getObject(UserModel.class);
        if(result.getStatus() == 0) {
            toast("修改成功");
            finish();
        }
        else {
            toast(result.getResult());
        }
    }

    private void init() {
        setActionBar("个人信息");
        sex = find(R.id.sex);
        nickname = find(R.id.nickname);
        birthday = find(R.id.birthday);
        edu = find(R.id.education);
        work = find(R.id.work);
        sign = find(R.id.signature);
        tvNickname = find(R.id.mynickname);
        tvSex = find(R.id.mysex);
        tvBirthday = find(R.id.mybirthday);
        tvEmail = find(R.id.mymail);
        tvEdu = find(R.id.myeducation);
        tvWork = find(R.id.mywork);
        tvSign = find(R.id.mysignature);
        sex.setOnClickListener(this);
        nickname.setOnClickListener(this);
        birthday.setOnClickListener(this);
        edu.setOnClickListener(this);
        work.setOnClickListener(this);
        sign.setOnClickListener(this);
        initView();
    }

    public void initView() {
        userModel = getUserModel();
        tvNickname.setText(userModel.getUsername());
        tvSex.setText(userModel.getSex());
        tvBirthday.setText(userModel.getBirthday());
        tvEmail.setText(userModel.getEmail());
        tvEdu.setText(userModel.getEducation());
        tvWork.setText(userModel.getWorking());
        tvSign.setText(userModel.getSignature());
    }

    @Override
    public void onClick(View v) {
        int layoutId = 0;
        switch (v.getId()) {
            case R.id.sex:
                layoutId = R.layout.setsex_layout;
                layoutName = "性别";
                break;
            case R.id.nickname:
                layoutId = R.layout.setname_activity;
                layoutName = "昵称";
                break;
            case R.id.birthday:
                layoutName = "生日";
                return;
            case R.id.work:
                layoutId = R.layout.setwork_layout;
                layoutName = "工作信息";
                break;
            case R.id.education:
                layoutId = R.layout.seteducation_layout;
                layoutName = "教育信息";
                break;
            case R.id.signature:
                layoutId = R.layout.setsn_layout;
                layoutName = "个性签名";
                break;
        }
        Bundle data = new Bundle();
        data.putInt("layoutId", layoutId);
        data.putInt("itemId", v.getId());
        data.putString("layoutName", layoutName);
        startActivity(SetInformationActivity.class, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.clear();
        //getMenuInflater().inflate(R.menu.add_plan, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onResume() {
        initView();
        super.onResume();
    }
}