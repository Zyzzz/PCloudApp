package imu.pcloud.app.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.model.LocalPlan;
import imu.pcloud.app.utils.DateTool;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by guyu on 2016/6/7.
 */
public class PlanItemAdapter extends BaseAdapter {
    private ArrayList<LocalPlan> planArrayList;
    private Activity activity;
    LayoutInflater layoutInflater;

    public PlanItemAdapter(ArrayList<LocalPlan> planArrayList, Activity activity) {
        this.planArrayList = planArrayList;
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());
    }
    @Override
    public int getCount() {
        return planArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocalPlan plan = planArrayList.get(position);
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.personal_list_item, null);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.title = (TextView) convertView.findViewById(R.id.plan_title);
            holder.content = (TextView) convertView.findViewById(R.id.plan_content);
            holder.back = convertView.findViewById(R.id.back);
            holder.back_2 = convertView.findViewById(R.id.back_2);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.startTime.setText(plan.getStartTimeString());
        holder.endTime.setText(plan.getEndTimeString());
        holder.title.setText(plan.getTitle() + "(来自:" + plan.getName() + ")");
        holder.content.setText(plan.getContent());
        float per = getTimeGone(plan);
        float left = 0;
        float right = 1;
        if(per == 0) {
            left = 0;
            right = 1;
            holder.back.setBackgroundResource(R.color.bl);
        }
        else if(per == 1) {
            left = 1;
            right = 0;
        }
        else {
            left = per * 50;
            right = 50 - left;
            holder.back.setBackgroundResource(R.drawable.back);
        }
        holder.back.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT
                , left));
        holder.back_2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT
                , right));

        return convertView;
    }

    class ViewHolder {
        TextView startTime;
        TextView endTime;
        TextView title;
        TextView content;
        View back;
        View back_2;
    }

    public float getTimeGone(LocalPlan plan) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String currentStr = df.format(new Date());
        Time start = DateTool.stringToTime(plan.getStartTimeString());
        Time end = DateTool.stringToTime(plan.getEndTimeString());
        Time current = DateTool.stringToTime(currentStr);
        long max = end.getTime() - start.getTime();
        long now = current.getTime() - start.getTime();
        if(current.before(start)) {
            return 0;
        }
        else if(current.after(end)) {
            return 1;
        }else {
            return (float)now / (float)max;
        }
    }
}
