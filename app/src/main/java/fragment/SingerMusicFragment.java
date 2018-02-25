package fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import utils.ToastUtil;

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
        final SingerMusicList list = ((SingerInfoActivity) getActivity()).getSingerMusicList();
        RecyclerView recyclerView = view.findViewById(R.id.rv_frag_singer_music);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new NetRecyclerViewAdapter(list.getSonglist(), list.getSonglist().get(0).getAuthor(),
                new NetRecyclerViewAdapter.ClickListener() {
                    @Override
                    public void moreListener(View view, int position) {
                        final Song_list songList = list.getSonglist().get(position);
                        BottomDialogUtil.showDialog(mContext, songList.getTitle(),
                                new Random().nextInt(10000) + 100, songList.getArtist_name(), songList.getAlbum_title(),
                                new BottomDialogUtil.DialogItemClickListener() {
                                    @Override
                                    public void OnItemClickListener(int index) {
                                        ToastUtil.showShort(mContext, index + "");
                                        switch (index) {
                                            case 1:
                                                break;
                                            case 2:
                                                break;
                                            case 3:
                                                break;
                                            case 4:
                                                break;
                                            case 5:
                                                break;
                                            case 6:
                                                break;
                                        }
                                    }
                                });
                    }

                    @Override
                    public void itemListener(View view, int position) {
                        ToastUtil.showShort(mContext, "play " + list.getSonglist().get(position).getTitle());
                    }
                }));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
