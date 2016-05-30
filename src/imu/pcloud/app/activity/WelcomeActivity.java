package imu.pcloud.app.activity;

import android.os.Bundle;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guyu on 2016/5/28.
 */
public class WelcomeActivity extends HttpActivity{

    private Timer timer = new Timer();
    private UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.welcome_layout);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                get("relogin", "cookies", getCookie());
            }
        }, 1500);
    }

    @Override
    protected void onSuccess() {
        user = getObject(UserModel.class);
        if(user.getStatus() == 0) {
            setUserId(user.getId());
            setUserMoodel(user);
            startActivity(MainActivity.class);
            finish();
        }
        else {
            toast(user.getResult());
            startActivity(LoginActivity.class);
            finish();
        }
    }

    @Override
    protected void onFailure() {
        super.onFailure();
        startActivity(LoginActivity.class);
        finish();
    }
}
