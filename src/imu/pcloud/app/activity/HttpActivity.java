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
import imu.pcloud.app.utils.SysApplication;
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
            if(prams[i + 1] == null)
                requestParams.put((String) prams[i], "");
            else
                requestParams.put((String) prams[i],  prams[i + 1].getClass().cast(prams[i + 1]));
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
        toast("SERVER ERROR");
    }

    protected void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    protected void setCookie(String cookie) {
        editor.putString("cookie", cookie);
        editor.commit();
    }

    protected void setUserId(int userId) {
        editor.putInt("userId", userId);
        editor.commit();
    }

    protected void setUserMoodel(UserModel userMoodel) {
        editor.putString("usermodel", gson.toJson(userMoodel));
        editor.commit();
    }

    protected UserModel getUserModel() {
        String userModelString = sharedPreferences.getString("usermodel", "");
        return gson.fromJson(userModelString, UserModel.class);
    }

    protected String getCookie() {
        return sharedPreferences.getString("cookie", "");
    }

    public int getUserId() {
        return sharedPreferences.getInt("userId", -1);
    }

    protected <T> void startActivity(Class<T> targetActivity) {
        SysApplication.getInstance().addActivity(this);
        startActivity(new Intent(getApplicationContext(), targetActivity));
    }

    protected <T> void startActivity(Class<T> targetActivity, Bundle savedInstanceState) {
        SysApplication.getInstance().addActivity(this);
        Intent intent = new Intent(getApplicationContext(), targetActivity);
        intent.putExtras(savedInstanceState);
        startActivity(intent);
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
            //toast(throwable.getMessage());
            HttpActivity.this.onFailure();
        }
    }

    public void setActionBar(int layoutId, String title) {
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
                layoutId, null);
        TextView textview=(TextView) actionbarLayout.findViewById(R.id.acText);
        textview.setText(title);
        myActionBar.setCustomView(actionbarLayout);
    }

    public void setActionBar(String title) {
        setActionBar(R.layout.actionbar_layout, title);
    }
}
