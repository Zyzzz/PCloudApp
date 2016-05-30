package imu.pcloud.app.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.AddPlanItemActivity;
import imu.pcloud.app.listener.OnPlanInputListener;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.utils.DateTool;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.Calendar;

/**
 * Created by guyu on 2016/5/26.
 */
public class AddItemFragment extends DialogFragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener{

    EditText title;
    TextView startTime;
    TextView endTime;
    EditText content;
    View touched;
    TimePickerDialog timePickerDialog;
    OnPlanInputListener onPlanInputListener;

    public void setOnPlanInputListener(OnPlanInputListener onPlanInputListener) {
        this.onPlanInputListener = onPlanInputListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        View view = inflater.inflate(R.layout.set_plan_item, container);
//        return view;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.set_plan_item, null);
        startTime = (TextView) view.findViewById(R.id.start_time);
        endTime = (TextView) view.findViewById(R.id.end_time);
        title = (EditText) view.findViewById(R.id.title);
        content = (EditText) view.findViewById(R.id.content);
        Plan plan = ((AddPlanItemActivity)getActivity()).getNowPlan();
        endTime.setOnClickListener(this);
        startTime.setOnClickListener(this);
        if(plan != null) {
            startTime.setText(plan.getStartTimeString());
            endTime.setText(plan.getEndTimeString());
            title.setText(plan.getTitle());
            content.setText(plan.getContent());
        }
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                if(judge() != true) {
                                    setDialogStatus(false, dialog);
                                    return;
                                }
                                Plan plan = new Plan(startTime.getText().toString(),
                                        endTime.getText().toString(),
                                        content.getText().toString(),
                                        title.getText().toString());
                                if(onPlanInputListener != null)
                                    onPlanInputListener.onPlanInputed(plan);
                                setDialogStatus(true, dialog);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setDialogStatus(true, dialog);
            }
        });
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        touched = v;
        if(v.getId() == R.id.end_time) {
            if(startTime.getText().length() == 0){
                Toast.makeText(getActivity(), "请先设置开始时间", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.Builder builder = new TimePickerDialog.Builder(getActivity());
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        // TODO Auto-generated method stub
        String sMinute = minute < 10 ? "0" + minute : "" + minute;
        String sHour = hour < 10 ? "0" + hour : "" + hour;
        switch (touched.getId()) {
            case R.id.start_time:
                startTime.setText(sHour + ":" + sMinute);
                break;
            case R.id.end_time:
                endTime.setText(sHour + ":" + sMinute);
                break;
        }
    }

    public void setDialogStatus(Boolean opened, DialogInterface dialog) {
        try
        {
            Field field = dialog.getClass()
                    .getSuperclass().getDeclaredField(
                            "mShowing" );
            field.setAccessible( true );
            // 将mShowing变量设为false，表示对话框已关闭
            field.set(dialog, opened);
            dialog.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean judge() {
        if(title.getText().length() == 0) {
            toast("标题不能为空");
            return false;
        }
        else if(startTime.getText().length() == 0) {
            toast("开始时间不能为空");
            return false;
        }
        else if (endTime.getText().length() == 0) {
            toast("结束时间不能为空");
            return false;
        }
        else if(content.getText().length() == 0) {
            content.setText(title.getText());
            return true;
        }
        else {
            Time end = DateTool.stringToTime(endTime.getText().toString());
            Time start = DateTool.stringToTime(startTime.getText().toString());
            if(end.before(start)) {
                toast("结束时间需要大于开始时间");
                return false;
            }
        }
        return true;
    }

    protected void toast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

}