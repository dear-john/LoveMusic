package base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private boolean canLoad = false;
    private boolean isPrepared = false;
    protected boolean hasLoaded = false;
    protected View view;
    protected Context mContext;
    protected Context mApplicationContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        if (canLoad && !hasLoaded) {
            lazyLoad();
            /*
            加载成功后，设置为true避免下次重复加载
            但是如果只是调用了lazyLoad()，但加载没有成功
            设置为true会导致下一次不再加载
             */
//            hasLoaded = true;
        }
        isPrepared = true;
        mContext = view.getContext();
        mApplicationContext = mContext.getApplicationContext();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && !hasLoaded) {
            canLoad = true;
        }
        if (getUserVisibleHint() && !hasLoaded && isPrepared) {
            lazyLoad();
        }
    }

    protected abstract void lazyLoad();

    protected abstract int getLayoutId();

}
