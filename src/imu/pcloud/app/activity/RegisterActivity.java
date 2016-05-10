package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import imu.pcloud.app.R;
import imu.pcloud.app.utils.HttpClient;
import org.apache.http.Header;

/**
 * Created by guyu on 2016/5/10.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

    TextView username;
    TextView email;
    TextView password;
    TextView repassword;
    Button register;
    RequestParams prams = new RequestParams();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    private void init() {
        setContentView(R.layout.register_activity);
        username = (TextView)findViewById(R.id.username);
        email = (TextView)findViewById(R.id.email);
        password = (TextView)findViewById(R.id.password);
        repassword = (TextView)findViewById(R.id.repassword);
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                prams.put("username", username.getText());
                prams.put("email", email.getText());
                prams.put("password", password.getText());
                prams.put("repassword", repassword.getText());
                HttpClient.get("register", prams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
        }
    }
}