package imu.pcloud.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.activity.PlanCircleActivity;
import imu.pcloud.app.activity.UserSharingActivity;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.utils.ImageUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/30.
 */
public class PlanCircleAdspter extends BaseAdapter {

    private List<Map<String, Object>> arrays = null;
    private Context mContext;
    private Button curDel_btn;
    private float x,ux;
    private PlanCircleActivity mActivity;
    ImageUtil imageUtil;
    public PlanCircleAdspter(PlanCircleActivity activity, Context mContext, List<Map<String, Object>> arrays) {
        this.mContext = mContext;
        this.arrays = arrays;
        mActivity = activity;
        imageUtil = new ImageUtil(mContext);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.plancircle_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.plancircle_name);
            viewHolder.planCilcleImage = (ImageView) view.findViewById(R.id.plancircle_image);
            viewHolder.planCilcleImage.setBackgroundDrawable(imageUtil.getIcon(position));
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

        viewHolder.tvTitle.setText((String)this.arrays.get(position).get("name"));

        return view;

    }
    final static class ViewHolder {
        TextView tvTitle;
        ImageView planCilcleImage;
        Button btnDel;
    }
}
