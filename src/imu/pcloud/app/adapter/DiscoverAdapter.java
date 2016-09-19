package imu.pcloud.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import imu.pcloud.app.R;
import imu.pcloud.app.been.Discover;
import imu.pcloud.app.utils.ImageUtil;

import java.util.ArrayList;

/**
 * Created by guyu on 2016/6/10.
 */
public class DiscoverAdapter extends BaseAdapter implements View.OnClickListener {

    ArrayList<Discover> discoverArrayList;
    LayoutInflater layoutInflater;
    Context context;
    OnItemClickListener onItemClickListener;
    public DiscoverAdapter(Context context, ArrayList<Discover> discoverArrayList) {
        this.discoverArrayList = discoverArrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return discoverArrayList.size();
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
        ViewHolder holder = null;
        Discover discover = discoverArrayList.get(position);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.discover_item, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.from = (TextView) convertView.findViewById(R.id.from);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.info = (TextView) convertView.findViewById(R.id.info);
            holder.fromView = convertView.findViewById(R.id.from_view);
            holder.comtentView = convertView.findViewById(R.id.comtent_view);
            holder.position = position;
            holder.fromView.setOnClickListener(this);
            holder.comtentView.setOnClickListener(this);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(discover.getPersonalPlan().getName());
        holder.icon.setBackgroundDrawable(new ImageUtil(context).getIcon(discover.getPlanCircle().getId()));
        holder.icon.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.from.setText("来自" + discover.getPlanCircle().getName());
        holder.content.setText(discover.getUser().getUsername() + " :  " + discover.getSharingRecord().getDiscribe());
        holder.info.setText(getInfo(discover.getSharingRecord().getLoadingTime(), discover.getCommentTime()));
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = 0;
        try {
            View parent = (View)v.getParent().getParent();
            ViewHolder holder = (ViewHolder) parent.getTag();
            position = holder.position;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if(onItemClickListener != null)
            onItemClickListener.onItemClick(position, v);
    }

    public class ViewHolder {
        ImageView icon;
        TextView from;
        TextView title;
        TextView content;
        TextView info;
        View fromView;
        View comtentView;
        int position;
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, View v);
    }

    public String getInfo(int loadTimes, long commentTimes) {
        return loadTimes + " 加载 · " + commentTimes + " 评论";
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDiscoverArrayList(ArrayList<Discover> list) {
        this.discoverArrayList.clear();
        this.discoverArrayList = list;
        notifyDataSetChanged();
    }
}
