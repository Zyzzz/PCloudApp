package imu.pcloud.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.HttpActivity;
import imu.pcloud.app.model.UserModel;
import imu.pcloud.app.utils.GsonTool;
import imu.pcloud.app.utils.HttpClient;
import imu.pcloud.app.utils.SysApplication;
import org.apache.http.Header;

import java.util.jar.Attributes;

/**
 * Created by guyu on 2016/5/11.
 */
abstract public class HttpFragment extends Fragment {
    protected String jsonString = null;
    protected MyAsyncHttpResponseHandler myHandler = new MyAsyncHttpResponseHandler();
    protected Gson gson = GsonTool.getGson();
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    private  String UserName;
    private  String UserPassword;
    final public static String SPACE = "           ";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sharedPreferences = getActivity().getSharedPreferences("userinfo", getActivity().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        
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

    abstract protected void onSuccess();

    protected  void  onFailure(){

    }


    protected void toast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    protected void setCookie(String cookie) {
        editor.putString("cookie", cookie);
        editor.commit();
    }

    protected String getCookie() {
        return sharedPreferences.getString("cookie", "");
    }

    protected void setUserId(int userId) {
        editor.putInt("userId", userId);
        editor.commit();
    }

    protected int getUserId() {
        return sharedPreferences.getInt("userId", -1);
    }

    protected void setUserMoodel(UserModel userMoodel) {
        editor.putString("usermodel", gson.toJson(userMoodel));
        editor.commit();
    }

    protected UserModel getUserModel() {
        String userModelString = sharedPreferences.getString("usermodel", "");
        return gson.fromJson(userModelString, UserModel.class);
    }

    class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            jsonString = new String(bytes);
            HttpFragment.this.onSuccess();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            toast("网络连接失败");
            HttpFragment.this.onFailure();
        }
    }

    protected <T> void startActivity(Class<T> targetActivity) {
        SysApplication.getInstance().addActivity((Activity) getActivity());
        startActivity(new Intent(getActivity(), targetActivity));
    }

    protected <T> void startActivity(Class<T> targetActivity, Bundle savedInstanceState) {
        SysApplication.getInstance().addActivity((Activity) getActivity());
        Intent intent = new Intent(getActivity(), targetActivity);
        intent.putExtras(savedInstanceState);
        startActivity(intent);
    }

}
