package imu.pcloud.app.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.UserSharingActivity;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.utils.ImageUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/29.
 */
public class AdspterHide extends BaseAdapter implements View.OnClickListener{
    private  List<Map<String, Object>> arrays = null;
    private Context mContext;
    LayoutInflater layoutInflater;
    private Button curDel_btn;
    private float x,ux;
    private UserSharingActivity mActivity;
    private List<PersonalPlan> planList;
    ImageUtil imageUtil;
    public AdspterHide(UserSharingActivity activity, Context mContext,  List<Map<String, Object>> arrays, List<PersonalPlan> planList) {
        this.mContext = mContext;
        this.arrays = arrays;
        mActivity = activity;
        layoutInflater = activity.getLayoutInflater();
        this.planList = planList;
        imageUtil = new ImageUtil(mContext);
  }
    public final class ViewHolder {
        ImageView user_sharing_image;
        View item;
        TextView user_sharing_name;
        //ImageView tick;
        int position;
    }
    public int getCount() {
        return this.arrays.size();
   }
    public Object getItem(int position) {
     return null;
      }
    public long getItemId(int position) {
        return position;
    }
     @Override
       public View getView(int position, View convertView, ViewGroup parent) {
            Integer planId = (Integer) arrays.get(position).get("planId");
            ViewHolder holder = null;
            LinearLayout linearLayout;
            LinearLayout backLayout;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.user_sharing_list_item, null);
                //holder.selector = (ImageView) convertView.findViewById(R.id.user_sharing_image);
                holder.item = convertView.findViewById(R.id.item);
                holder.user_sharing_name = (TextView) convertView.findViewById(R.id.user_sharing_name);
                holder.position = position;
                holder.user_sharing_image = (ImageView) convertView.findViewById(R.id.user_sharing_image);
                holder.user_sharing_image.setBackgroundDrawable(imageUtil.getIcon(planId));
                linearLayout = (LinearLayout) convertView.findViewById(R.id.conversatinListview_front);
                final Button btn_delete = (Button) convertView.findViewById(R.id.UserSharing_delete);
                //final Button btn_share = (Button) convertView.findViewById(R.id.conversationlist_share);
                btn_delete.setOnClickListener(this);
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
                        int bottomWidth = btn_delete.getWidth();
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
                                if (isOpen) {
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
                                    //finalHolder.item.performClick();
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
          //  holder.selector.setOnClickListener(this);
           // holder.item.setOnClickListener(this);
            //holder.item.setClickable(false);
            holder.user_sharing_name.setText((String)arrays.get(position).get("name"));
            return convertView;
        }
        @Override
        public void onClick(View v) {
            View view = (View) v.getParent().getParent();
            int position = ((ViewHolder)view.getTag()).position;
            mActivity.deleteUserSharing(position);
            //myOnClickListener.onClickItem(v, position);
        }
}

