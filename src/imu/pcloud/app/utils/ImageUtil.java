package imu.pcloud.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import imu.pcloud.app.been.Image;
import imu.pcloud.app.model.ImageModel;
import imu.pcloud.app.model.UserModel;
import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

/**
 * Created by guyu on 2016/6/6.
 */
public class ImageUtil {
    Context context;
    AssetManager assetManager;
    String jsonString;
    Blob image;
    SQLiteDatabase db;
    int mode;
    Gson gson = new GsonBuilder().create();
    int imageId;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    OnSetListener onSetListener;
    OnLoadListener onLoadListener;

    public interface OnSetListener {
        void onSet(ImageModel imageModel);
    }

    public interface OnLoadListener {
        void onLoad(ImageModel imageModel);
    }

    public void setOnSetListener(OnSetListener onSetListener) {
        this.onSetListener = onSetListener;
    }
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }
    protected MyAsyncHttpResponseHandler myHandler = new MyAsyncHttpResponseHandler();
    public ImageUtil(Context context) {
        this.context = context;
        assetManager = context.getAssets();
        preferences = context.getSharedPreferences("image_cache", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public BitmapDrawable getIcon(int id) {
        id = id % 11;
        try {
            InputStream assetFile = assetManager.open("icon/" + id + ".png");
            Bitmap bitmap = BitmapFactory.decodeStream(assetFile);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            assetFile.close();
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BitmapDrawable getHeader(int userId, int imageId) {
        //imageId = 0;
        if(imageId == 0) {
            int id = userId;
            switch (id) {
                case 21:id = 6;break;
                case 18:id = 3;break;
            }
            id = id % 11;
            try {
                InputStream assetFile = assetManager.open("icon/" + id + ".png");
                Bitmap bitmap = BitmapFactory.decodeStream(assetFile);
                BitmapDrawable drawable = new BitmapDrawable(bitmap);
                assetFile.close();
                return drawable;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            BitmapDrawable drawable;
            if(hasImage(imageId)) {
                drawable = new BitmapDrawable(loadImage(imageId));
                if(drawable.getBitmap() == null) {
                    downloadImage(imageId);
                    return null;
                }
                return  drawable;
            }
            else  {
                downloadImage(imageId);
                return null;
            }
        }
    }



    public void setHeader(String cookies, Bitmap bitmap) {
        String bitmapString = ImageEncodeTool.bitmapToBase64(bitmap);
        if(!bitmap.isRecycled()){
            bitmap.recycle();//记得释放资源，否则会内存溢出
        }
        get("setUserHeader", "imageFile", bitmapString, "cookies", cookies);
        mode = 0;
    }

    public void downloadImage(int imageId) {
        this.imageId = imageId;
        get("getImage", "imageId", imageId);
        mode = 1;
    }

    public void saveImage(ImageModel imageModel) {
        Image image = imageModel.getImage();
        editor.putString("image" + image.getId(), image.getImageFile());
        editor.commit();
    }

    public Bitmap loadImage(int imageId) {
        String string = preferences.getString("image" + imageId, null);
        if(string == null)
            return null;
        try {
            return ImageEncodeTool.base64ToBitmap(string);
        }   catch(Exception e)  {
            e.printStackTrace();
            return null;
        }
    }

    public void clearImage() {
        editor.clear();
        editor.commit();
    }

    public boolean hasImage(int imageId) {
        String string = preferences.getString("image" + imageId, null);
        return string != null;
    }

    class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            jsonString = new String(bytes);
            if(mode == 1) {
                ImageModel imageModel = gson.fromJson(jsonString, ImageModel.class);
                saveImage(imageModel);
                if(onLoadListener != null)
                    onLoadListener.onLoad(imageModel);
            }
            else {
                ImageModel imageModel = gson.fromJson(jsonString, ImageModel.class);
                saveImage(imageModel);
                if(onSetListener != null)
                    onSetListener.onSet(imageModel);
            }
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            //toast(throwable.getMessage());
        }
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
}
