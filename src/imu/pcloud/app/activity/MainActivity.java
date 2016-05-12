package imu.pcloud.app.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import imu.pcloud.app.R;
import imu.pcloud.app.fragment.PersonalFragment;
import imu.pcloud.app.fragment.SettingFragment;
import imu.pcloud.app.fragment.TeamFragment;
import imu.pcloud.app.fragment.ZoneFragment;


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
    private Fragment mtab01;
    private Fragment mtab02;
    private Fragment mtab03;
    private Fragment mtab04;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        innitView();
        innitEvents();
        setSelcet(0);
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
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        HideFragment(transaction);
        switch (i){
            case 0:
                if(mtab01==null){
                    mtab01=new PersonalFragment();
                    transaction.add(R.id.content, mtab01);
                }
                else{
                    transaction.show(mtab01);
                }
                mImgPersonal.setImageResource(R.drawable.ic_launcher);
                break;
            case 1:
                if(mtab02==null){
                    mtab02=new TeamFragment();
                    transaction.add(R.id.content, mtab02);
                }
                else{
                    transaction.show(mtab02);
                }
                mImgTeam.setImageResource(R.drawable.ic_launcher);
                break;
            case 2:
                if(mtab03==null){
                    mtab03=new ZoneFragment();
                    transaction.add(R.id.content, mtab03);
                }
                else{
                    transaction.show(mtab03);
                }
                mImgZone.setImageResource(R.drawable.ic_launcher);
                break;
            case 3:
                if(mtab04==null){
                    mtab04=new SettingFragment();
                    transaction.add(R.id.content, mtab04);
                }
                else{
                    transaction.show(mtab04);
                }
                mImgSetting.setImageResource(R.drawable.ic_launcher);
                break;
            default:
                break;
        }
        transaction.commit();

    }

    private void HideFragment(FragmentTransaction transaction) {
        if(mtab01!=null){
            transaction.hide(mtab01);
        }
        if(mtab02!=null){
            transaction.hide(mtab02);
        }
        if(mtab03!=null){
            transaction.hide(mtab03);
        }
        if(mtab04!=null){
            transaction.hide(mtab04);
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
