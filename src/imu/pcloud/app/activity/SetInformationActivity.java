package imu.pcloud.app.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.model.UserModel;
import imu.pcloud.app.utils.DateTool;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by guyu on 2016/6/2.
 */
public class SetInformationActivity extends HttpActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private int layoutId = 0;
    private int itemId = 0;
    EditText editText;
    TextView textView;
    View editor1;
    View editor2;
    UserModel userModel;
    String birthday = null;
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
                textView = (TextView) findViewById(R.id.setbirth);
                textView.setOnClickListener(this);
                textView.setText(DateTool.getRealDate(userModel.getBirthday()));
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
                    userModel.setBirthday(birthday);
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
            case R.id.setbirth:
                showDateDialog();
                break;
        }
    }

    public void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        Date birthday;
        if(this.birthday == null)
            birthday = DateTool.stringToDate(userModel.getBirthday());
        else
            birthday = DateTool.stringToDate(this.birthday);
        if(birthday != null)
            calendar.setTimeInMillis(birthday.getTime());
        DatePickerDialog timePickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_YEAR));
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String yearStr = year + "";
        String monthStr = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;
        String dayStr = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        birthday = yearStr + "-" + monthStr + "-" + dayStr;
        textView.setText(DateTool.getRealDate(birthday));
    }
}
