package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import base.BaseFragment;


public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> baseFragmentList;
    private List<String> titleList;

    public MyFragmentAdapter(FragmentManager fm, List<BaseFragment> baseFragments) {
        super(fm);
        baseFragmentList = baseFragments;
    }

    public MyFragmentAdapter(FragmentManager fm, List<BaseFragment> baseFragments, List<String> titles) {
        this(fm, baseFragments);
        titleList = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return baseFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return baseFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null) {
            return titleList.get(position);
        }
        return null;
    }
}
