package imu.pcloud.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import imu.pcloud.app.R;
import imu.pcloud.app.utils.ClipImageLayout;
import imu.pcloud.app.utils.ImageTools;

import java.io.File;

/**
 * Created by Administrator on 2016/6/7.
 */
public class ClipActivity extends HttpActivity {
    private ClipImageLayout mClipImageLayout;
    private String path;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.clipimage_layout);
        setActionBar("图片");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadingDialog=new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
        path=getIntent().getStringExtra("path");
        if(TextUtils.isEmpty(path)||!(new File(path).exists())){
            Toast.makeText(this, "图片加载失败",Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap= ImageTools.convertToBitmap(path, 600,600);
        if(bitmap==null){
            Toast.makeText(this, "图片加载失败",Toast.LENGTH_SHORT).show();
            return;
        }
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        mClipImageLayout.setBitmap(bitmap);
        ((Button)findViewById(R.id.id_action_clip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = mClipImageLayout.clip();
                        String path= Environment.getExternalStorageDirectory()+"/ClipHeadPhoto/cache/"+System.currentTimeMillis()+ ".png";
                        ImageTools.savePhotoToSDCard(bitmap,path);
                        loadingDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("path",path);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onSuccess() {

    }
}

