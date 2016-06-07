package imu.pcloud.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;
import imu.pcloud.app.utils.DateTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by guyu on 2016/5/27.
 */
public class InformationActivity extends HttpActivity implements View.OnClickListener{
    private View nickname;
    private View sex;
    private View birthday;
    private View sign;
    private View edu;
    private View work;
    private TextView tvNickname;
    private TextView tvSex;
    private TextView tvEmail;
    private TextView tvBirthday;
    private TextView tvSign;
    private TextView tvEdu;
    private TextView tvWork;
    private ImageView header;
    UserModel userModel;
    String layoutName;


    PopupWindow popWindow;
    LayoutInflater layoutInflater;
    TextView photograph;
    TextView albums ;
    LinearLayout cancel;
    public static final int PHOTOZOOM = 0; // 相册/拍照
    public static final int PHOTOTAKE = 1; // 相册/拍照
    public static final int IMAGE_COMPLETE = 2; // 结果
    public static final int CROPREQCODE = 3; // 截取

    private String photoSavePath;//保存路径
    private String photoSaveName;//图pian名
    private String path;//图片全路径


    public TextView getTvNickname() {
        return tvNickname;
    }

    public void setTvNickname(TextView tvNickname) {
        this.tvNickname = tvNickname;
    }

    public TextView getTvSex() {
        return tvSex;
    }

    public void setTvSex(TextView tvSex) {
        this.tvSex = tvSex;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_activity);
        init();
    }

    @Override
    protected void onSuccess() {
        UserModel result = getObject(UserModel.class);
        if(result.getStatus() == 0) {
            toast("修改成功");
            finish();
        }
        else {
            toast(result.getResult());
        }
    }

    private void init() {
        layoutInflater = getLayoutInflater();
        setActionBar(R.layout.actionbar_check_layout, "个人信息");
        sex = find(R.id.sex);
        nickname = find(R.id.nickname);
        birthday = find(R.id.birthday);
        edu = find(R.id.education);
        work = find(R.id.work);
        sign = find(R.id.signature);
        tvNickname = find(R.id.mynickname);
        tvSex = find(R.id.mysex);
        tvBirthday = find(R.id.mybirthday);
        tvEmail = find(R.id.mymail);
        tvEdu = find(R.id.myeducation);
        tvWork = find(R.id.mywork);
        tvSign = find(R.id.mysignature);
        header = find(R.id.header_image);
        header.setBackgroundDrawable(imageUtil.getHeader(getUserId()));
        header.setOnClickListener(this);
        sex.setOnClickListener(this);
        nickname.setOnClickListener(this);
        birthday.setOnClickListener(this);
        edu.setOnClickListener(this);
        work.setOnClickListener(this);
        sign.setOnClickListener(this);
        initView();
    }

    public void initView() {
        userModel = getUserModel();
        tvNickname.setText(userModel.getUsername());
        tvSex.setText(userModel.getSex());
        tvBirthday.setText(userModel.getBirthday());
        tvEmail.setText(userModel.getEmail());
        tvEdu.setText(userModel.getEducation());
        tvWork.setText(userModel.getWorking());
        tvSign.setText(userModel.getSignature());
    }

    @Override
    public void onClick(View v) {
        int layoutId = 0;
        switch (v.getId()) {
            case R.id.sex:
                layoutId = R.layout.setsex_layout;
                layoutName = "性别";
                break;
            case R.id.nickname:
                layoutId = R.layout.setname_activity;
                layoutName = "昵称";
                break;
            case R.id.birthday:
                layoutName = "生日";
                layoutId = R.layout.setbirth_layout;
                break;
            case R.id.work:
                layoutId = R.layout.setwork_layout;
                layoutName = "工作信息";
                break;
            case R.id.education:
                layoutId = R.layout.seteducation_layout;
                layoutName = "教育信息";
                break;
            case R.id.signature:
                layoutId = R.layout.setsn_layout;
                layoutName = "个性签名";
                break;
            case R.id.header_image:
                showPopupWindow(header);
//              break;
        }
        Bundle data = new Bundle();
        data.putInt("layoutId", layoutId);
        data.putInt("itemId", v.getId());
        data.putString("layoutName", layoutName);
        startActivity(SetInformationActivity.class, data);
    }
    private void showPopupWindow(View parent){
        if (popWindow == null) {
            View view = layoutInflater.inflate(R.layout.pop_select_layout,null);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
            initPop(view);
        }
        popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    private void initPop(View view) {
        photograph = (TextView) view.findViewById(R.id.photograph);//拍照
        albums = (TextView) view.findViewById(R.id.albums);//相册
        cancel= (LinearLayout) view.findViewById(R.id.cancel);//取消
        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                photoSaveName =String.valueOf(System.currentTimeMillis()) + ".png";
                Uri imageUri = null;
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(photoSavePath,photoSaveName));
                openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(openCameraIntent, PHOTOTAKE);
            }
        });
        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(openAlbumIntent, PHOTOZOOM);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.clear();
        //getMenuInflater().inflate(R.menu.add_plan, menu);
        return true;
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

    @Override
    protected void onResume() {
        initView();
        super.onResume();
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri uri = null;
        switch (requestCode) {
            case PHOTOZOOM://相册
                if (data==null) {
                    return;
                }
                uri = data.getData();
                String[] proj = { MediaStore.Images.Media.DATA };
                Cursor cursor = managedQuery(uri, proj, null, null,null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);// 图片在的路径
                Intent intent3=new Intent(this,ClipActivity.class);
                intent3.putExtra("path", path);
                startActivityForResult(intent3, IMAGE_COMPLETE);
                break;
            case PHOTOTAKE://拍照
                path=photoSavePath+photoSaveName;
                uri = Uri.fromFile(new File(path));
                Intent intent2=new Intent(this,ClipActivity.class);
                intent2.putExtra("path", path);
                startActivityForResult(intent2, IMAGE_COMPLETE);
                break;
            case IMAGE_COMPLETE:
                final String temppath = data.getStringExtra("path");
                header.setImageBitmap(getLoacalBitmap(temppath));
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}