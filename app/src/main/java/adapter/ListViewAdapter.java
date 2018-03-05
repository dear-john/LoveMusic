package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bean.NavItem;

/**
 * Created by 李君 on 2018/3/5.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;

    public ListViewAdapter(Context context) {
        mContext = context;
    }

    private List<NavItem> mNavItemList = new ArrayList<>(
            Arrays.asList(
                    new NavItem("我的消息", R.drawable.msg),
                    new NavItem("我的好友", R.drawable.friend),
                    new NavItem("定时停止播放", R.drawable.stop_ontime),
                    new NavItem("扫一扫", R.drawable.scan)
            ));

    @Override
    public int getCount() {
        return mNavItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
            viewHolder.mTextView = convertView.findViewById(R.id.tv_lv_item_title);
            viewHolder.mImageView = convertView.findViewById(R.id.iv_lv_item_icon);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        NavItem navItem = mNavItemList.get(position);
        viewHolder.mTextView.setText(navItem.getTitle());
        viewHolder.mImageView.setImageResource(navItem.getIconRes());
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
    }
}

