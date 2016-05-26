package imu.pcloud.app.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;
import imu.pcloud.app.utils.GsonTool;
import imu.pcloud.app.utils.HttpClient;
import imu.pcloud.app.utils.ViewFinder;
import org.apache.http.Header;
import org.apache.http.util.ExceptionUtils;

import java.lang.reflect.Field;

/**
 * Created by guyu on 2016/5/11.
 */
abstract public class HttpActivity extends Activity {
    protected String jsonString = null;
    protected MyAsyncHttpResponseHandler myHandler = new MyAsyncHttpResponseHandler();
    protected Gson gson = GsonTool.getGson();
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    protected ViewFinder finder;
    protected ActionBar myActionBar;
    final public static String SPACE = "         ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        finder = new ViewFinder(this);
        setOverflowShowingAlways();
    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <V extends View> V find(final int id) {
        return finder.find(id);
    }

    void get(String url, Object... prams) {
        RequestParams requestParams = new RequestParams();
        for (int i = 0; i < prams.length; i += 2) {
            requestParams.put((String) prams[i],
                    prams[i + 1].getClass().cast(prams[i + 1]));
        }
        HttpClient.get(url, requestParams, myHandler);
    }

    protected <T> T getObject(Class<T> t) {
        try {
            return gson.fromJson(jsonString, t);
        } catch(Exception e) {
            System.out.println(jsonString);
            return null;
        }
    }

    abstract protected void onSuccess();

    protected void onFailure(){

    }

    protected void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    protected void setCookie(String cookie) {
        editor.putString("cookie", cookie);
        editor.commit();
    }

    protected String getCookie() {
        return sharedPreferences.getString("cookie", "");
    }

    protected <T> void startActivity(Class<T> targetActivity) {
        startActivity(new Intent(getApplicationContext(), targetActivity));
    }

    protected UserModel relogin() {
        RequestParams prams = new RequestParams();
        prams.put("cookies", getCookie());
        final UserModel[] result = new UserModel[1];
        HttpClient.get("relogin", prams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                result[0] = gson.fromJson(new String(bytes), UserModel.class);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
        return result[0];
    }

    class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            jsonString = new String(bytes);
            HttpActivity.this.onSuccess();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            toast("网络连接失败");
            HttpActivity.this.onFailure();
        }
    }

    public void setActionBar(String title, String space) {
        myActionBar = getActionBar();
        // 返回箭头（默认不显示）
        myActionBar.setDisplayHomeAsUpEnabled(true);
        // 左侧图标点击事件使能
        myActionBar.setHomeButtonEnabled(true);
        // 使左上角图标(系统)是否显示
        myActionBar.setDisplayShowHomeEnabled(false);
        myActionBar.setTitle("返回");
        // 显示标题
        myActionBar.setDisplayShowTitleEnabled(true);
        myActionBar.setDisplayShowCustomEnabled(true);//显示自定义视图
        View actionbarLayout = LayoutInflater.from(this).inflate(
                R.layout.actionbar_layout, null);
        TextView textview=(TextView) actionbarLayout.findViewById(R.id.acText);
        textview.setText(space + title);
        myActionBar.setCustomView(actionbarLayout);
    }

    public void setActionBar(String title) {
        setActionBar(title, "");
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.clear();
//        getMenuInflater().inflate(R.menu.personal, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
}
