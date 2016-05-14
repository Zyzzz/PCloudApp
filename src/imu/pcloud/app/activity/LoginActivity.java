package imu.pcloud.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends HttpActivity implements View.OnClickListener {

    private Button register;
    private Button login;
    private TextView email;
    private TextView password;
    private static UserModel user;
    private int loginFlag = 0;
    private Timer timer = new Timer();
    private SharedPreferences spf;
    private Context context;

    public static UserModel getUser() {
        return user;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                get("relogin", "cookies", getCookie());
            }
        }, 2000);

    }

    protected void init() {
        setContentView(R.layout.login_layout);
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
        if (user.getStatus() == 0) {
            if(loginFlag != 0)
                setCookie(user.getCookies());
                startActivity(MainActivity.class);
        } else {
            if (loginFlag == 0) {
                init();
                loginFlag = 1;
            }
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
                break;
        }
    }
    private void saveLoginInfo(Context context,String email,String password){
        spf=context.getSharedPreferences("config", context.MODE_PRIVATE);
        SharedPreferences.Editor editor=spf.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.commit();
    }

    private  void rememberUser(){
        SharedPreferences sharedPre=getSharedPreferences("config", MODE_PRIVATE);
        String Email=sharedPre.getString("email", "");
        String Password=sharedPre.getString("password", "");
        email.setText(Email);
        password.setText(Password);

    }


}

