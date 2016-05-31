package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;

/**
 * Created by guyu on 2016/5/10.
 */
public class RegisterActivity extends HttpActivity implements View.OnClickListener {

    TextView username;
    TextView email;
    TextView password;
    TextView repassword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setActionBar("注册");
        setContentView(R.layout.register_activity);
        username = find(R.id.username);
        email = find(R.id.email);
        password = find(R.id.password);
        repassword = find(R.id.repassword);
        register = find(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                get("register", "username", username.getText(), "email", email.getText(), "password", password.getText(), "repassword", repassword.getText());
                break;
        }
    }

    @Override
    protected void onSuccess() {
        UserModel user = getObject(UserModel.class);
        if (user.getStatus() == 0) {
            toast("注册成功");
            this.finish();
        } else {
            toast(user.getResult());
        }
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
}