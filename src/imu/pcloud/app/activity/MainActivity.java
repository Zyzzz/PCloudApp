package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import imu.pcloud.app.R;


/**
 * Created by acer on 2016/5/11.
 */
public class MainActivity extends HttpActivity implements View.OnClickListener{

    private LinearLayout mTabPersonal;
    private LinearLayout mTabTeam;
    private LinearLayout mTabZone;
    private LinearLayout mTabSetting;
    private ImageButton mImgPersonal;
    private ImageButton mImgTeam;
    private ImageButton mImgZone;
    private ImageButton mImgSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        innitView();
        innitEvents();
    }

    @Override
    protected void OnSuccess() {

    }

    private void innitEvents() {
        mTabPersonal.setOnClickListener(this);
        mTabTeam.setOnClickListener(this);
        mTabZone.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);
    }

    private void innitView() {
        mTabPersonal= find(R.id.personal);
        mTabTeam=(LinearLayout)findViewById(R.id.team);
        mTabZone=(LinearLayout)findViewById(R.id.zone);
        mTabSetting=(LinearLayout)findViewById(R.id.setting);
        mImgPersonal = (ImageButton) findViewById(R.id.button1);
        mImgTeam = (ImageButton) findViewById(R.id.button2);
        mImgZone = (ImageButton) findViewById(R.id.button3);
        mImgSetting = (ImageButton) findViewById(R.id.button4);
    }
    private void setSelcet(int i){
        //把图片设置为亮的
        //设置内容区域
        switch (i){
            case 0:
                mImgPersonal.setImageResource(R.drawable.ic_launcher);
                break;
            case 1:
                mImgTeam.setImageResource(R.drawable.ic_launcher);
                break;
            case 2:
                mImgZone.setImageResource(R.drawable.ic_launcher);
                break;
            case 3:
                mImgSetting.setImageResource(R.drawable.ic_launcher);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        RsetImgs();
        switch (v.getId()){
            case R.id.personal:
                setSelcet(0);
                break;
            case R.id.team:
                setSelcet(1);
                break;
            case R.id.zone:
                setSelcet(2);
                break;
            case R.id.setting:
                setSelcet(3);
                break;
            default:
                break;
        }

    }

    private void RsetImgs() {
        mImgPersonal.setImageResource(R.drawable.ic_launcher);
        mImgTeam.setImageResource(R.drawable.ic_launcher);
        mImgZone.setImageResource(R.drawable.ic_launcher);
        mImgSetting.setImageResource(R.drawable.ic_launcher);
    }
}
