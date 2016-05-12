package imu.pcloud.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;

public class LoginActivity extends HttpActivity implements View.OnClickListener {

    private Button register;
    private Button login;
    private TextView email;
    private TextView password;
    private static UserModel user;

    public static UserModel getUser() {
        return user;
    }
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setContentView(R.layout.login_layout);
        if(getCookie().length() > 1)
            get("relogin", "cookies", getCookie());
        register = find(R.id.register);
        login = find(R.id.login);
        email = find(R.id.email_text);
        password = find(R.id.password_text);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    protected void OnSuccess() {
        user = getObject(UserModel.class);
        if(user.getStatus() == 0) {
            toast("登录成功");
            setCookie(user.getCookies());
            startActivity(MainActivity.class);
        }
        else {
            toast(user.getResult());
            setCookie("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                get("login", "email", email.getText(), "password", password.getText());
                break;
            case R.id.register:
                startActivity(RegisterActivity.class);
                finish();
                break;
        }
    }
}
