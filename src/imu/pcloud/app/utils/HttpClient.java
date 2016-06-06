package imu.pcloud.app.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import imu.pcloud.app.activity.HttpActivity;
import org.apache.http.protocol.HTTP;

/**
 * Created by guyu on 2016/5/10.
 */
public abstract class HttpClient {
    private static AsyncHttpClient client = new AsyncHttpClient();
    /*private static final String BASE_URL = "http://183.175.12.153:33333/PCloudServer/";
    //*/private static final String BASE_URL = "http://115.28.53.244:8080/PCloudServer/";

    public HttpClient() {
        client.setConnectTimeout(3000);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.setContentEncoding(HTTP.UTF_8);
        client.setConnectTimeout(3000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.setContentEncoding(HTTP.UTF_8);
        client.setConnectTimeout(3000);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl + ".action";
    }
}
