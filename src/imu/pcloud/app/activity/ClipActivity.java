package imu.pcloud.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import imu.pcloud.app.R;
import imu.pcloud.app.utils.ClipImageLayout;
import imu.pcloud.app.utils.ImageTools;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/7.
 */
public class ClipActivity extends HttpActivity {
    private ClipImageLayout mClipImageLayout;
    private String path;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clipimage_layout);
        //这步必须要加
        setActionBar(R.layout.actionbar_check_layout, "设置头像");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadingDialog=new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
        path=getIntent().getStringExtra("path");
        if(TextUtils.isEmpty(path)||!(new File(path).exists())){
            Toast.makeText(this, "图片加载失败",Toast.LENGTH_SHORT).show();
            return;
        }
        int degree = getBitmapDegree(path);
        Bitmap bitmap = ImageTools.convertToBitmap(path, 720, 720);
        bitmap = ImageTools.rotateBitmap(bitmap, degree);
        if(bitmap==null){
            Toast.makeText(this, "图片加载失败",Toast.LENGTH_SHORT).show();
            return;
        }
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        mClipImageLayout.setBitmap(bitmap);
        findViewById(R.id.id_action_clip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = mClipImageLayout.clip();
                        bitmap = ImageTools.clipBitmap(bitmap, 100, 100);
                        String path= Environment.getExternalStorageDirectory()+"/ClipHeadPhoto/cache/"+System.currentTimeMillis()+ ".png";
                        ImageTools.savePhotoToSDCard(bitmap,path);
                        loadingDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("path",path);
                        setResult(RESULT_OK, intent);
                        if(!bitmap.isRecycled()){
                            bitmap.recycle();//记得释放资源，否则会内存溢出
                        }
                        finish();
                    }
                }).start();
            }
        });
    }
    @Override
    protected void onSuccess() {

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}

