package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spring_ballet.lovemusic.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import Presenter.MusicContract;
import Presenter.SingerInfoMusicPresenter;
import adapter.MyFragmentAdapter;
import base.BaseActivity;
import base.BaseFragment;
import bean.SingerInfo;
import bean.SingerInfoEvent;
import utils.ShareUtil;
import utils.ToastUtil;

/**
 * Created by 李君 on 2018/4/5.
 */

public class SingerIntroduceFragment extends BaseFragment implements MusicContract.View<SingerInfo> {

    private ViewPager mViewPager;
    private SingerInfo mSingerInfo;
    private TabLayout tabLayout;
    private ImageView ivSingerBg;
    private TextView tvSingerName;
    private String uid;
    private MusicContract.Presenter mPresenter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        hasLoaded = true;
        initWidgets();
        initViewPager();
        mPresenter = new SingerInfoMusicPresenter(uid, this);
        mPresenter.loadData();
    }

    public static SingerIntroduceFragment newInstance(String uid) {
        SingerIntroduceFragment fragment = new SingerIntroduceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uid = getArguments().getString("uid");
        }
    }

    protected void initWidgets() {
        view.findViewById(R.id.iv_singer_info_back).setOnClickListener(this);
        view.findViewById(R.id.iv_singer_share).setOnClickListener(this);
        tvSingerName = view.findViewById(R.id.tv_singer_name);
        tabLayout = view.findViewById(R.id.tablayout_singer_info);
        mViewPager = view.findViewById(R.id.vp_singer_info);
        ivSingerBg = view.findViewById(R.id.iv_default_singer_bg);
    }

    private void initViewPager() {
        List<BaseFragment> baseFragmentList = new ArrayList<>(4);
        baseFragmentList.add(SingerMusicFragment.newInstance(uid));
        baseFragmentList.add(new SingerAlbumFragment());
        baseFragmentList.add(new SingerVideoFragment());
        baseFragmentList.add(new SingerInfoFragment());

        List<String> titleList = new ArrayList<>(4);
        titleList.add("单曲");
        titleList.add("专辑");
        titleList.add("视频");
        titleList.add("歌手信息");
        mViewPager.setAdapter(new MyFragmentAdapter(getChildFragmentManager(), baseFragmentList, titleList));
        mViewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_singer_introduce;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_singer_info_back:
                ((BaseActivity) mContext).showNewView(new LocalMusicDetailFragment());
                break;
            case R.id.iv_singer_share:
                ShareUtil.share(mContext, "分享歌手" + mSingerInfo.getName()
                        + " " + mSingerInfo.getUrl() + "(来自@"
                        + mContext.getResources().getString(R.string.app_name) + ")");
                break;
        }
    }

    @Override
    public void refreshView(List<SingerInfo> list) {
        SingerInfoEvent event = new SingerInfoEvent();
        if (list != null && list.size() > 0 && (mSingerInfo = list.get(0)) != null) {
            tvSingerName.setText(mSingerInfo.getName());
            String url = mSingerInfo.getAvatar_s1000();
            if (TextUtils.isEmpty(url)) {
                url = mSingerInfo.getAvatar_s500();
                if (TextUtils.isEmpty(url)) url = mSingerInfo.getAvatar_big();
            }
            if (!TextUtils.isEmpty(url)) Glide.with(mContext).load(url).into(ivSingerBg);
            event.setInfo(mSingerInfo.getIntro());
        } else {
            ToastUtil.showShort(mContext, getResources().getString(R.string.loading_failed));
            event.setInfo("暂无歌手简介");
        }
        EventBus.getDefault().post(event);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
        super.onDestroy();
    }

}
