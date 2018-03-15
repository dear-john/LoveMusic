package fragment;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.spring_ballet.lovemusic.R;
import com.spring_ballet.lovemusic.SingerInfoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

import adapter.NetRecyclerViewAdapter;
import base.BaseFragment;
import bean.MessageEvent;
import bean.SingerMusicList;
import bean.Song_list;
import utils.BottomDialogUtil;
import utils.PlayOnlineMusicUtil;

/**
 * Created by 李君 on 2018/2/23.
 */

public class SingerMusicFragment extends BaseFragment {

    @Override
    protected void lazyLoad() {
        hasLoaded = true;
        EventBus.getDefault().register(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataLoadFinish(MessageEvent event) {
        if (event.isMusicDataFinish()) {
            final View loadingView = view.findViewById(R.id.layout_loading);
            ImageView imageView = loadingView.findViewById(R.id.iv_loading);
            final AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
            if (drawable != null && !drawable.isRunning())
                drawable.start();
            final SingerMusicList list = ((SingerInfoActivity) getActivity()).getSingerMusicList();
            RecyclerView recyclerView = view.findViewById(R.id.rv_frag_singer_music);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(new NetRecyclerViewAdapter(list.getSonglist(), new NetRecyclerViewAdapter.ClickListener() {
                @Override
                public void moreListener(View view, int position) {
                    final Song_list songList = list.getSonglist().get(position);
                    new BottomDialogUtil().showDialog(getActivity(), songList.getTitle(),
                            new Random().nextInt(10000) + 100,
                            list.getSonglist().get(position).getAuthor(), songList.getAlbum_title(), "singer");
                }

                @Override
                public void itemListener(View view, int position) {
                    Song_list songList = list.getSonglist().get(position);
                    PlayOnlineMusicUtil.playMusic((SingerInfoActivity) getActivity(), songList.getSong_id(),
                            songList.getPic_small(), songList.getTitle(), songList.getAuthor());
                }
            }));
            if (drawable != null && drawable.isRunning()) {
                drawable.stop();
                loadingView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
