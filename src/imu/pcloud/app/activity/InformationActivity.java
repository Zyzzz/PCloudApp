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
    private TextView tvNickname;
    private TextView tvSex;

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
        tvNickname = find(R.id.mynickname);
        tvSex = find(R.id.mysex);
        sex.setOnClickListener(this);
        nickname.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sex:
                break;
            case R.id.nickname:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.add_plan, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.confirm:
                get("setInformation", "cookies", getCookie(),
                        "sex", tvSex.getText(), "birthday", "",
                        "education", "", "working", "",
                        "signature", "", "name", tvNickname.getText());
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}