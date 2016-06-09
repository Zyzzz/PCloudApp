package imu.pcloud.app.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.model.TimeConfig;
import imu.pcloud.app.utils.ImageUtil;

/**
 * Created by acer on 2016/5/29.
 */
public class SetTimeFragment extends DialogFragment implements View.OnClickListener {
    private Button []buttons = new Button[8];
    private TextView selftextView;
    private String Time;
    public TimeConfig timeConfig = new TimeConfig();
    private OnTimeSetListener onTimeSetListener;
    public int planMode;
    public int planId;
    public int flag = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.settime_dialog_layout, null);
        initButtons(view);
        builder.setView(view).
                setTitle("计划进行的时间").
                setPositiveButton("确定",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeConfig.setPlanMode(planMode);
                        timeConfig.setPlanId(planId);
                        timeConfig.setWeeks(getButtonStatus());
                        if(onTimeSetListener != null)
                            onTimeSetListener.onTimeSet(timeConfig);
                    }
        }).setNegativeButton("取消", null);
        if(flag == 1) {
            init(timeConfig);
            builder.setNeutralButton("停止", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timeConfig.setWeeks(null);
                    if(onTimeSetListener != null)
                        onTimeSetListener.onTimeSet(timeConfig);
                }
            });
        }
        return builder.create();
    }

    private void initButtons(View view) {
        buttons[0] = (Button) view.findViewById(R.id.button8);
        buttons[1] = (Button) view.findViewById(R.id.button1);
        buttons[2] = (Button) view.findViewById(R.id.button2);
        buttons[3] = (Button) view.findViewById(R.id.button3);
        buttons[4] = (Button) view.findViewById(R.id.button4);
        buttons[5] = (Button) view.findViewById(R.id.button5);
        buttons[6] = (Button) view.findViewById(R.id.button6);
        buttons[7] = (Button) view.findViewById(R.id.button7);
        for(Button button: buttons) {
            button.setOnClickListener(this);
        }
    }

    private void init(TimeConfig timeConfig) {
        initButtonsStatus(timeConfig.getWeeks());
        this.planId = timeConfig.getPlanId();
        this.planMode = timeConfig.getPlanMode();
    }

    private void initButtonsStatus(boolean []status) {
        int i = 0;
        for(Button button: buttons) {
            button.setSelected(status[i]);
            i++;
        }
    }

    private int getButtonPos(View view) {
        int i = 0;
        for(Button button: buttons) {
            if(button.getId() == view.getId())
                return i;
            i++;
        }
        return -1;
    }

    public boolean[] getButtonStatus() {
        boolean []status = new boolean[8];
        int i = 0;
        int times = 0;
        for(Button button: buttons) {
            times += button.isSelected() ? 1 : 0;
            status[i] = button.isSelected();
            i++;
        }
        if(times == 0)
            return null;
        return status;
    }

    @Override
    public void onClick(View v) {
        int pos = getButtonPos(v);
        if(pos < 0)
            return;
        switch (pos) {
            case 0:
                setButtonsSelected(!v.isSelected());
                break;
            default:
                v.setSelected(!v.isSelected());
        }
    }

    public void setButtonsSelected(boolean status) {
        for(Button button:buttons) {
            button.setSelected(status);
        }
    }

    public interface OnTimeSetListener {
        public void onTimeSet(TimeConfig timeConfig);
    }

    public void setOnTimeSetListener(OnTimeSetListener onTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener;
    }

    public void set(int planId, int planMode) {
        this.planId = planId;
        this.planMode = planMode;
    }

    public void set(TimeConfig timeConfig) {
        this.timeConfig = timeConfig;
        flag = 1;
    }
}
