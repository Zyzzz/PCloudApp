package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;

/**
 * Created by guyu on 2016/6/2.
 */
public class SetInformationActivity extends HttpActivity implements View.OnClickListener{

    private int layoutId = 0;
    private int itemId = 0;
    EditText editText;
    View editor1;
    View editor2;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        Bundle data = getIntent().getExtras();
        layoutId = data.getInt("layoutId", R.layout.setname_activity);
        itemId = data.getInt("itemId", R.id.nickname);
        setActionBar(data.getString("layoutName"), "");
        userModel = getUserModel();
        setContentView(layoutId);
        switch (itemId) {
            case R.id.sex:
                editor1 = findViewById(R.id.men);
                editor2 = findViewById(R.id.women);
                editor1.setOnClickListener(this);
                editor2.setOnClickListener(this);
                if(userModel.getSex() == null) break;
                else if(userModel.getSex().equals("男") || userModel.getSex().equals("女")) {
                    boolean sex = userModel.getSex().equals("男");
                    find(R.id.men_selector).setSelected(sex);
                    find(R.id.women_selector).setSelected(!sex);
                }
                break;
            case R.id.nickname:
                editText = (EditText) findViewById(R.id.nickname);
                editText.setText(userModel.getUsername());
                break;
            case R.id.birthday:
                break;
            case R.id.work:
                editText = (EditText) findViewById(R.id.setwork);
                editText.setText(userModel.getWorking());
                break;
            case R.id.education:
                editText = (EditText) findViewById(R.id.seteducation);
                editText.setText(userModel.getEducation());
                break;
            case R.id.signature:
                editText = (EditText) findViewById(R.id.setsignatrue);
                editText.setText(userModel.getSignature());
                break;
        }
    }

    @Override
    protected void onSuccess() {
        UserModel result = getObject(UserModel.class);
        if(result.getStatus() != 0) {
            toast(result.getResult());
        }
        else {
            toast("修改成功");
            setUserMoodel(result);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getItemId() == R.id.save) {
            switch (itemId) {
                case R.id.sex:
                    break;
                case R.id.nickname:
                    userModel.setUsername(editText.getText().toString());
                    break;
                case R.id.birthday:
                    break;
                case R.id.work:
                    userModel.setWorking(editText.getText().toString());
                    break;
                case R.id.education:
                    userModel.setEducation(editText.getText().toString());
                    break;
                case R.id.signature:
                    userModel.setSignature(editText.getText().toString());
                    break;
            }
            saveInformation();
        }
        else {
            finish();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void saveInformation() {
        get("setInformation",
                "cookies", getCookie(),
                "sex", userModel.getSex(),
                "birthday", userModel.getBirthday(),
                "education", userModel.getEducation(),
                "working", userModel.getWorking(),
                "signature", userModel.getSignature(),
                "name", userModel.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.men:
                userModel.setSex("男");
                find(R.id.men_selector).setSelected(true);
                find(R.id.women_selector).setSelected(false);
                break;
            case R.id.women:
                userModel.setSex("女");
                find(R.id.men_selector).setSelected(false);
                find(R.id.women_selector).setSelected(true);
                break;
        }
    }
}
