package imu.pcloud.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import imu.pcloud.app.R;

public class LoginActivity extends HttpActivity {

    private Button register;
    private Button login;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        register = find(R.id.register);
    }

    @Override
    protected void OnSuccess() {

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
