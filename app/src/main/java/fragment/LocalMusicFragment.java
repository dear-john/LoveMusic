package fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.spring_ballet.lovemusic.LocalMusicActivity;
import com.spring_ballet.lovemusic.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import base.BaseFragment;
import bean.MessageEvent;
import utils.IntentUtil;
import utils.SharedPreferencesUtil;
import utils.ToastUtil;


public class LocalMusicFragment extends BaseFragment {

    private TextView localMusicNumberTv;
    private View localMusicLayout;
    private View recentPlayLayout;
    private View downloadLayout;
    private View radioLayout;
    private View collectLayout;

    @Override
    protected void lazyLoad() {
        //加载成功后，设置为true避免下次重复加载
        hasLoaded = true;

        EventBus.getDefault().register(this);

        initWidgets();
        initListeners();
    }

    private void initWidgets() {
        localMusicLayout = view.findViewById(R.id.layout_local_music);
        localMusicNumberTv = view.findViewById(R.id.tv_local_music_number);
        int number = SharedPreferencesUtil.getIntData(mContext, "LocalMusicNumber");
        if (number != SharedPreferencesUtil.DEFAULT_INT_VALUE) {
            String text = "(" + number + ")";
            localMusicNumberTv.setText(text);
        }
        recentPlayLayout = view.findViewById(R.id.layout_recent_play);
        TextView recentPlayNumberTv = view.findViewById(R.id.tv_recent_paly_number);
        downloadLayout = view.findViewById(R.id.layout_download_music);
        TextView downloadNumberTv = view.findViewById(R.id.tv_download_music_number);
        radioLayout = view.findViewById(R.id.layout_radio);
        TextView radioTv = view.findViewById(R.id.tv_radio_number);
        collectLayout = view.findViewById(R.id.layout_collection);
        TextView collectTv = view.findViewById(R.id.tv_collection_number);
    }

    private void initListeners() {
        localMusicLayout.setOnClickListener(this);
        recentPlayLayout.setOnClickListener(this);
        downloadLayout.setOnClickListener(this);
        radioLayout.setOnClickListener(this);
        collectLayout.setOnClickListener(this);
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.local_music_frag;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_local_music:
                if (checkPermission())
                    IntentUtil.gotoActivity(mContext, LocalMusicActivity.class);
                break;
            case R.id.layout_recent_play:
                loadRecentPlayData();
                break;
            case R.id.layout_download_music:
                loadDownloadData();
                break;
            case R.id.layout_radio:
                loadRadioData();
                break;
            case R.id.layout_collection:
                loadCollectionData();
                break;
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showShort(mContext, "暂未授权，本功能不能使用");
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String text = "(" + event.getLocalMusicNumber() + ")";
        localMusicNumberTv.setText(text);
    }

    private void loadCollectionData() {

    }

    private void loadRadioData() {

    }

    private void loadDownloadData() {

    }

    private void loadRecentPlayData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
