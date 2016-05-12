package imu.pcloud.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import imu.pcloud.app.model.UserModel;
import imu.pcloud.app.utils.HttpClient;
import imu.pcloud.app.utils.ViewFinder;
import org.apache.http.Header;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * Created by guyu on 2016/5/11.
 */
abstract public class HttpActivity extends Activity {
    protected String jsonString = null;
    protected MyAsyncHttpResponseHandler myHandler = new MyAsyncHttpResponseHandler();
    protected Gson gson = new GsonBuilder().create();
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    protected ViewFinder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        finder = new ViewFinder(this);
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
        return gson.fromJson(jsonString, t);
    }

    abstract protected void OnSuccess();

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

    protected <T> void startActivity(Class<T> tartActivity) {
        startActivity(new Intent(getApplicationContext(), tartActivity));
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
            OnSuccess();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

        }
    }
}
