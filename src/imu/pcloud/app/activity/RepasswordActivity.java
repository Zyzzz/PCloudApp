package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import imu.pcloud.app.R;
import imu.pcloud.app.model.BaseModel;
import imu.pcloud.app.model.UserModel;

/**
 * Created by guyu on 2016/5/28.
 */
public class RepasswordActivity extends HttpActivity implements View.OnClickListener{

    EditText oldpassword;
    EditText newpassword;
    EditText repassword;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.repassword_activity);
        setActionBar("重置密码");
        oldpassword = find(R.id.oldpassword);
        newpassword = find(R.id.newpassword);
        repassword = find(R.id.repassword);
        confirm = find(R.id.confirm);
        confirm.setOnClickListener(this);
    }

    @Override
    protected void onSuccess() {
        BaseModel result = getObject(BaseModel.class);
        if(result.getStatus() == 0) {
            toast("重置密码成功");
            UserModel user = getUserModel();
            user.setPassword(newpassword.getText().toString());
            setUserMoodel(user);
            finish();
        }
        else {
            toast(result.getResult());
        }
    }

    @Override
    public void onClick(View v) {
        get("resetPassword", "cookies", getCookie(), "oldPassword", oldpassword.getText(), "newPassword", newpassword.getText(),  "rePassword", repassword.getText());
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        finish();
        return super.onMenuItemSelected(featureId, item);
    }
}
