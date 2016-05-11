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
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        register = find(R.id.register);
        login = find(R.id.login);
        email = find(R.id.email_text);
        password = find(R.id.password_text);
    }

    @Override
    protected void OnSuccess() {
        UserModel model = getObject(UserModel.class);
        if(model.getStatus() != 0) {
            toast(model.getResult());

        }
        else {
            toast("登录成功");
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                get("login", "email", email.getText(), "password", password.getText());
        }
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.text, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_refresh:
                Intent intent1 = new Intent();
                intent1.setClass(LoginActivity.this, Findpad.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
