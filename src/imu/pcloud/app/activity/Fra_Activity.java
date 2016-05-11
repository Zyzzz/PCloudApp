package imu.pcloud.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import imu.pcloud.app.R;


/**
 * Created by acer on 2016/5/11.
 */
public class Fra_Activity extends Activity {

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
    }

    private void innitView() {
        mTabPersonal=(LinearLayout) findViewById(R.id.personal);
        mTabTeam=(LinearLayout)findViewById(R.id.team);
        mTabZone=(LinearLayout)findViewById(R.id.zone);
        mTabSetting=(LinearLayout)findViewById(R.id.setting);
        mImgPersonal = (ImageButton) findViewById(R.id.button1);
        mImgTeam = (ImageButton) findViewById(R.id.button2);
        mImgZone = (ImageButton) findViewById(R.id.button3);
        mImgSetting = (ImageButton) findViewById(R.id.button4);
    }


}
