package imu.pcloud.app.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.AllPlanActivity;
import imu.pcloud.app.been.PersonalPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyu on 2016/5/30.
 */
public class AllPlanAdapter extends BaseAdapter implements View.OnClickListener
{

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
        LinearLayout linearLayout;
        LinearLayout backLayout;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.allplan_list_item, null);
            holder.selector = (ImageView) convertView.findViewById(R.id.selector);
            holder.item = convertView.findViewById(R.id.item);
            holder.plan_name = (TextView) convertView.findViewById(R.id.plan_name);
            holder.tick = (ImageView) convertView.findViewById(R.id.tick);
            holder.position = position;
            linearLayout = (LinearLayout) convertView.findViewById(R.id.conversatinListview_front);
            final Button btn_delete = (Button) convertView.findViewById(R.id.conversationlist_delete);
            final Button btn_share = (Button) convertView.findViewById(R.id.conversationlist_share);
            btn_delete.setOnClickListener(this);
            if(personalPlanArrayList.get(position).getUserId() != ((AllPlanActivity)context).getUserId()) {
                btn_share.setBackgroundResource(R.color.gray_l);
                btn_share.setText("他人计划\n无法分享");
            } else {
                btn_share.setOnClickListener(this);
            }
            final ViewHolder finalHolder = holder;
            linearLayout.setOnTouchListener(new View.OnTouchListener() {
                private Point pointDownPoint;
                private Point pointUpPoint;
                private boolean isdelete;
                boolean result = false;
                boolean isOpen = false;
                int downX;
                int downY;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int bottomWidth = btn_delete.getWidth() * 2;
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Log.i("", "ACTION_DOWN");
                            downX = (int) event.getRawX();
                            downY = (int) event.getRawY();
                            result = true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // Log.i("", "ACTION_MOVE");
                            // if (isAniming)
                            // break;
                            int dx = (int) (event.getRawX() - downX);
                            // Log.i("", "dy___" + dx);
                            if (isOpen ) {
                                // 打开状态
                                // 向右滑动
                                if (dx > 0 && dx < bottomWidth) {
                                    v.setTranslationX(dx - bottomWidth);
                                    // 允许移动，阻止点击
                                    result = true;
                                }
                            } else {
                                // 闭合状态
                                // 向左移动
                                if (dx < 0 && Math.abs(dx) < bottomWidth) {
                                    v.setTranslationX(dx);
                                    // 允许移动，阻止点击
                                    result = true;
                                }
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            // Log.i("", "ACTION_UP" + v.getTranslationX());
                            // 获取已经移动的
                            float ddx = v.getTranslationX();
                            int nowY = (int) event.getRawY();
                            // 判断打开还是关闭
                            if(ddx == 0  && Math.abs(nowY - downY) < 5) {
                                finalHolder.item.performClick();
                                return result;
                            }
                            if (ddx <= 0 && ddx > -(bottomWidth / 2)) {
                                // 关闭
                                ObjectAnimator oa1 = ObjectAnimator.ofFloat(v, "translationX", ddx, 0).setDuration(100);
                                oa1.start();
                                oa1.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        isOpen = false;
                                        result = false;
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                        isOpen = false;
                                        result = false;
                                    }
                                });
                            }
                            if (ddx <= -(bottomWidth / 2) && ddx > -bottomWidth) {
                                // 打开
                                ObjectAnimator oa1 = ObjectAnimator.ofFloat(v, "translationX", ddx, -bottomWidth)
                                        .setDuration(100);
                                oa1.start();
                                result = true;
                                isOpen = true;
                            }
                            break;
                    }
                    return result;
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.selector.setOnClickListener(this);
        holder.item.setOnClickListener(this);
        holder.item.setClickable(false);
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
        View view = (View) v.getParent().getParent();
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
