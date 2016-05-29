package imu.pcloud.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import imu.pcloud.app.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/28.
 */
public class MyAdspterhasButton extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyAdspterhasButton(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Zujian {
        public ImageView image;
        public TextView title;
        public Button button;
        //public Button view;
        // public TextView info;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;
        if (convertView == null) {
            zujian = new Zujian();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.user_sharing_list_item, null);

            //  zujian.image = (ImageView) convertView.findViewById(R.id.image);
            zujian.title = (TextView) convertView.findViewById(R.id.user_sharing_name);
            zujian.button =(Button) convertView.findViewById(R.id.btn_delete);
            //zujian.button.setFocusable(false);
            zujian.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.print("12345678979797984979494");
                }
            });
            //zujian.view=(Button)convertView.findViewById(R.id.view);
            //zujian.info = (TextView) convertView.findViewById(R.id.info);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        //绑定数据
        //zujian.image.setBackgroundResource((Integer) data.get(position).get("image"));
        zujian.title.setText((String) data.get(position).get("name"));
        //zujian.info.setText((String) data.get(position).get("info"));
        return convertView;
    }
}
