package fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.spring_ballet.lovemusic.LocalMusicActivity;
import com.spring_ballet.lovemusic.R;

import java.util.List;

import base.BaseFragment;
import bean.LocalMusic;
import utils.DialogUtil;
import utils.IntentUtil;
import utils.LocalMusicUtil;
import utils.ToastUtil;


public class LocalMusicFragment extends BaseFragment {

    @Override
    protected void lazyLoad() {
        //加载成功后，设置为true避免下次重复加载
        hasLoaded = true;
        checkPermission();
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            loadData();
        }
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.local_music_frag;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadData();
        } else {
            DialogUtil.showDialog(getActivity(), "是否进入设置进行授权?");
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                loadData();
            }
        }
    }

    private void loadData() {
        View localMusicLayout = view.findViewById(R.id.layout_local_music);
        TextView localMusicNumberTv = view.findViewById(R.id.tv_local_music_number);
        View recentPlayLayout = view.findViewById(R.id.layout_recent_play);
        TextView recentPlayNumberTv = view.findViewById(R.id.tv_recent_paly_number);
        View downloadLayout = view.findViewById(R.id.layout_download_music);
        TextView downloadNumberTv = view.findViewById(R.id.tv_download_music_number);
        View radioLayout = view.findViewById(R.id.layout_radio);
        TextView radioTv = view.findViewById(R.id.tv_radio_number);
        View collectLayout = view.findViewById(R.id.layout_collection);
        TextView collectTv = view.findViewById(R.id.tv_collection_number);
        localMusicLayout.setOnClickListener(this);
        recentPlayLayout.setOnClickListener(this);
        downloadLayout.setOnClickListener(this);
        radioLayout.setOnClickListener(this);
        collectLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_local_music:
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

    private void loadCollectionData() {

    }

    private void loadRadioData() {

    }

    private void loadDownloadData() {

    }

    private void loadRecentPlayData() {

    }
}
