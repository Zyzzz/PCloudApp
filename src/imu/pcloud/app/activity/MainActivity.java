package imu.pcloud.app.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.PlanCircle;
import imu.pcloud.app.fragment.PersonalFragment;
import imu.pcloud.app.fragment.SettingFragment;
import imu.pcloud.app.fragment.TeamFragment;
import imu.pcloud.app.fragment.ZoneFragment;
import imu.pcloud.app.model.PlanCircleList;

import java.util.ArrayList;
import java.util.List;
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/


/**
 * Created by acer on 2016/5/11.
 */
public class MainActivity extends HttpActivity implements View.OnClickListener {

    private LinearLayout mTabPersonal;
    private LinearLayout mTabTeam;
    private LinearLayout mTabZone;
    private LinearLayout mTabSetting;
    private ImageView mImgPersonal;
    private ImageView mImgTeam;
    private ImageView mImgZone;
    private ImageView mImgSetting;
    private Fragment mFragPersonal;
    private Fragment mFragZone;
    private Fragment mFragTeam;
    private Fragment mFragSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        innitView();
        innitEvents();
        setSelcet(0);
        setPlanCircles();
        get("getPlanCircleList");
    }



    @Override
    protected void onSuccess() {
        PlanCircleList planCircleList = getObject(PlanCircleList.class);
        if(planCircleList.getStatus() ==0) {
            planCircles = planCircleList.getPlanCircles();
            putPlanCircles();
        }
    }

    private void innitEvents() {
        mTabPersonal.setOnClickListener(this);
        mTabTeam.setOnClickListener(this);
        mTabZone.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);
    }

    private void innitView() {
        mTabPersonal = find(R.id.personal);
        mTabTeam = (LinearLayout) findViewById(R.id.team);
        mTabZone = (LinearLayout) findViewById(R.id.zone);
        mTabSetting = (LinearLayout) findViewById(R.id.setting);
        mImgPersonal = (ImageView) findViewById(R.id.button1);
        mImgTeam = (ImageView) findViewById(R.id.button2);
        mImgZone = (ImageView) findViewById(R.id.button3);
        mImgSetting = (ImageView) findViewById(R.id.button4);
    }

    private void setSelcet(int i) {
        //把图片设置为亮的
        //设置内容区域
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        rsetImgs();
        switch (i) {
            case 0:
                if (mFragPersonal == null) {
                    mFragPersonal = new PersonalFragment();
                    transaction.add(R.id.content, mFragPersonal);

                } else {
                    transaction.show(mFragPersonal);
                }
                mImgPersonal.setSelected(true);
                break;
            case 1:
                if (mFragZone == null) {
                    mFragZone = new TeamFragment();
                    transaction.add(R.id.content, mFragZone);
                } else {
                    transaction.show(mFragZone);

                }
                mImgZone.setSelected(true);
                break;
            case 2:
                if (mFragTeam == null) {
                    mFragTeam = new ZoneFragment();
                    transaction.add(R.id.content, mFragTeam);
                } else {
                    transaction.show(mFragTeam);

                }
                mImgTeam.setSelected(true);
                break;
            case 3:
                if (mFragSetting == null) {
                    mFragSetting = new SettingFragment();
                    transaction.add(R.id.content, mFragSetting);
                } else {
                    transaction.show(mFragSetting);

                }
                mImgSetting.setSelected(true);
                break;
            default:
                break;
        }
        transaction.commit();

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mFragPersonal != null) {
            transaction.hide(mFragPersonal);
        }
        if (mFragZone != null) {
            transaction.hide(mFragZone);
        }
        if (mFragTeam != null) {
            transaction.hide(mFragTeam);
        }
        if (mFragSetting != null) {
            transaction.hide(mFragSetting);
        }
    }

    @Override
    public void onClick(View v) {
        rsetImgs();
        switch (v.getId()) {
            case R.id.personal:
                setSelcet(0);
                break;
            case R.id.zone:
                setSelcet(1);
                break;
            case R.id.team:
                setSelcet(2);
                break;
            case R.id.setting:
                setSelcet(3);
                break;
            default:
                break;
        }

    }

    private void rsetImgs() {
        mImgPersonal.setSelected(false);
        mImgTeam.setSelected(false);
        mImgZone.setSelected(false);
        mImgSetting.setSelected(false);
    }

}
