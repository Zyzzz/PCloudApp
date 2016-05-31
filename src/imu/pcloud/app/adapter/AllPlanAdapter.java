package imu.pcloud.app.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyu on 2016/5/30.
 */
public class AllPlanAdapter extends BaseAdapter implements View.OnClickListener{

    LayoutInflater layoutInflater;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    ArrayList<PersonalPlan> personalPlanArrayList;
    ArrayList<Integer> selectedPlanId;
    MyOnClickListener myOnClickListener;
    int userId;
    Context context;
    public AllPlanAdapter(Context context, ArrayList<PersonalPlan> personalPlanArrayList, int userId) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.personalPlanArrayList = personalPlanArrayList;
        this.userId = userId;
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    @Override
    public int getCount() {
        return pList.size();
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
        getData();
        ViewHolder holder = null;
        PersonalPlan plan = personalPlanArrayList.get(position);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.allplan_list_item, null);
            holder.selector = (ImageView) convertView.findViewById(R.id.selector);
            holder.item = convertView.findViewById(R.id.item);
            holder.plan_name = (TextView) convertView.findViewById(R.id.plan_name);
            holder.tick = (ImageView) convertView.findViewById(R.id.tick);
            holder.position = position;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.selector.setOnClickListener(this);
        holder.item.setOnClickListener(this);
        holder.plan_name.setText((String)pList.get(position).get("plan_name"));
        if(selectedPlanId.indexOf(plan.getId()) != -1) {
            holder.selector.setSelected(true);
        }
        else {
            holder.selector.setSelected(false);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        View view = (View) v.getParent();
        int position = ((ViewHolder)view.getTag()).position;
        myOnClickListener.onClickItem(v, position);
    }

    public final class ViewHolder {
        ImageView selector;
        View item;
        TextView plan_name;
        ImageView tick;
        int position;
    }

    public List<Map<String,Object>> getData() {
        pList.clear();
        for (PersonalPlan plan:personalPlanArrayList)
        {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("plan_name", plan.getName());
            pList.add(map);
        }
        return pList;
    }

    public interface MyOnClickListener {
        void onClickItem(View v, int position);
    }

    @Override
    public void notifyDataSetChanged() {
        getData();
        setSelectedPlanId();
        super.notifyDataSetChanged();
    }

    public void setSelectedPlanId() {
        Gson gson = new GsonBuilder().create();
        SharedPreferences sharedPreferences = context.getSharedPreferences("userinfo", context.MODE_PRIVATE);
        selectedPlanId = gson.fromJson(
                sharedPreferences.getString("selectedPlanId" + userId, ""),
                new TypeToken<ArrayList<Integer>>() {
                }.getType());
        if(selectedPlanId == null){
            selectedPlanId = new ArrayList<Integer>();
        }
    }
}
