package fragment;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;
import com.spring_ballet.lovemusic.SingerInfoActivity;

import java.util.List;

import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import base.BaseFragment;
import bean.SingerMusicList;
import model.MusicData;
import model.OnDataLoadFinished;
import model.SingerMusicListMusicDataImpl;
import utils.ToastUtil;

/**
 * Created by 李君 on 2018/2/23.
 */

public class SingerMusicFragment extends BaseFragment {

    private View loadingView;
    private AnimationDrawable drawable;
    private ImageView imageView;

    @Override
    protected void lazyLoad() {
        hasLoaded = true;
        loadingView = view.findViewById(R.id.layout_loading);
        imageView = loadingView.findViewById(R.id.iv_loading);
        drawable = (AnimationDrawable) imageView.getBackground();
        if (drawable != null && !drawable.isRunning())
            drawable.start();
        loadData();
    }

    private void loadData() {
        MusicData<SingerMusicList> data = new SingerMusicListMusicDataImpl(((SingerInfoActivity) getActivity()).getUid());
        data.getMusicData(new OnDataLoadFinished<SingerMusicList>() {
            @Override
            public void onLoadFinished(List<SingerMusicList> list) {
                SingerMusicList singerMusicList;
                if (list != null && list.size() > 0 && (singerMusicList = list.get(0)) != null) {
                    RecyclerView recyclerView = view.findViewById(R.id.rv_frag_singer_music);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    recyclerView.setAdapter(new MusicRecyclerViewAdapter((BaseActivity) getActivity(),
                            true, singerMusicList.getSonglist(), null));
                    if (drawable != null && drawable.isRunning()) {
                        drawable.stop();
                        loadingView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else onLoadFailed();
            }
        });
    }

    private void onLoadFailed() {
        TextView textView = loadingView.findViewById(R.id.tv_loading);
        textView.setText(R.string.loading_failed);
        ToastUtil.showShort(mContext, getResources().getString(R.string.loading_failed));
        if (drawable != null && drawable.isRunning()) {
            drawable.stop();
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_singer_music;
    }

    @Override
    public void onClick(View v) {

    }

}
