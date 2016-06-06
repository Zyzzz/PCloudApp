package imu.pcloud.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by guyu on 2016/6/6.
 */
public class ImageUtil {
    Context context;
    AssetManager assetManager;

    public ImageUtil(Context context) {
        this.context = context;
        assetManager = context.getAssets();
    }

    public BitmapDrawable getIcon(int id) {
        //id = id
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

    public BitmapDrawable getHeader(int id) {
        switch (id) {
            case 21:id = 6;break;
        }
        id = id % 11;
        try {
            InputStream assetFile = assetManager.open("header/" + id + ".png");
            Bitmap bitmap = BitmapFactory.decodeStream(assetFile);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);;
            assetFile.close();
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
