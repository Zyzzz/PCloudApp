package imu.pcloud.app.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import imu.pcloud.app.R;

/**
 * Created by acer on 2016/5/29.
 */
public class SetTimeFragment extends DialogFragment {
    private RadioButton oneDay;
    private RadioButton threeDay;
    private RadioButton oneMonth;
    private RadioButton oneWeek;
    private TextView selftextView;
    private String Time;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.settime_dialog_layout, null);
        oneDay= (RadioButton) view.findViewById(R.id.oneday);
        threeDay= (RadioButton) view.findViewById(R.id.threeday);
        oneWeek= (RadioButton) view.findViewById(R.id.oneweek);
        oneMonth= (RadioButton) view.findViewById(R.id.onemonth);
        GetChoose();
        builder.setView(view).setPositiveButton("确定",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }


        }).setNegativeButton("取消", null);
        return super.onCreateDialog(savedInstanceState);
    }

    private void GetChoose() {
        if(oneDay.isChecked()) Time = "一天";
        else if(threeDay.isChecked()) Time = "三天";
        else if(oneWeek.isChecked()) Time = "七天";
        else if(oneMonth.isChecked()) Time = "三十天";
        else
            Time=selftextView.getText().toString();
    }
}
