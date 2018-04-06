package fragment;

import android.view.View;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import base.BaseActivity;
import base.BaseFragment;
import bean.MessageEvent;
import utils.SharedPreferencesUtil;


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
        int number = SharedPreferencesUtil.getIntData(getActivity(), "LocalMusicNumber");
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
    protected int getLayoutId() {
        return R.layout.local_music_frag;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_local_music:
                ((BaseActivity) getActivity()).showNewView(new LocalMusicDetailFragment());
                break;
            case R.id.layout_recent_play:
                break;
            case R.id.layout_download_music:
                break;
            case R.id.layout_radio:
                break;
            case R.id.layout_collection:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String text = "(" + event.getLocalMusicNumber() + ")";
        localMusicNumberTv.setText(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
