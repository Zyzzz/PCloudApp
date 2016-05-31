package imu.pcloud.app.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.UserSharingActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/29.
 */
public class AdspterHide extends BaseAdapter{
    private  List<Map<String, Object>> arrays = null;
    private Context mContext;
    private Button curDel_btn;
    private float x,ux;
    private UserSharingActivity mActivity;
    public AdspterHide(UserSharingActivity activity, Context mContext,  List<Map<String, Object>> arrays) {
        this.mContext = mContext;
        this.arrays = arrays;
        mActivity = activity;
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

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.user_sharing_list_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.user_sharing_name);
            viewHolder.btnDel = (Button) view.findViewById(R.id.btn_delete);
            view.setTag(viewHolder);
          } else {
                  viewHolder = (ViewHolder) view.getTag();
        }
      //换掉了原来listview中的onItemClick
        view.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                                 // TODO Auto-generated method stub
                                //mActivity.openActivity();
                           }
         });
        //viewHolder.tvTitle.setText((String); data.get(position).get("name"));
          //为每一个view项设置触控监听
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                final ViewHolder holder = (ViewHolder) v.getTag();
                //当按下时处理
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //                    //设置背景为选中状态
                    //                    v.setBackgroundResource(R.drawable.mm_listitem_pressed);
                    //获取按下时的x轴坐标
                    x = event.getX();
                    //判断之前是否出现了删除按钮如果存在就隐藏
                    if (curDel_btn != null) {
                        if(curDel_btn.getVisibility() == View.VISIBLE){
                            curDel_btn.setVisibility(View.GONE);
                            return true;
                        }
                    }
                 } else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
                     //设置背景为未选中正常状态
                    //v.setBackgroundResource(R.drawable.mm_listitem_simple);
                    //获取松开时的x坐标
                    ux = event.getX();
                    //判断当前项中按钮控件不为空时
                    if (holder.btnDel != null) {
                        //按下和松开绝对值差当大于20时显示删除按钮，否则不显示
                        if (Math.abs(x - ux) > 20) {
                            holder.btnDel.setVisibility(View.VISIBLE);
                            curDel_btn = holder.btnDel;
                            return true;
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {//当滑动时背景为选中状态
                    return true;
                    //v.setBackgroundResource(R.drawable.mm_listitem_pressed);
                } else {//其他模式
                    //设置背景为未选中正常状态
                    //v.setBackgroundResource(R.drawable.mm_listitem_simple);
                             }

                return false;
            }
        });
        viewHolder.tvTitle.setText((String)this.arrays.get(position).get("name"));
             //为删除按钮添加监听事件，实现点击删除按钮时删除该项
        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                     if(curDel_btn!=null)
                         curDel_btn.setVisibility(View.GONE);
                         mActivity.deleteUserSharing(position);
                         arrays.remove(position);
                      //notifyDataSetChanged();
                     }
         });
          return view;
    }
         final static class ViewHolder {
             TextView tvTitle;
             Button btnDel;
        }
 }
