package base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {
    private boolean canLoad = false;
    private boolean isPrepared = false;
    protected boolean hasLoaded = false;
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        if (canLoad && !hasLoaded) {
            lazyLoad();
            hasLoaded = true;
        }
        isPrepared = true;
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
            hasLoaded = true;
        }
    }

    protected abstract void lazyLoad();

    protected abstract void refresh();

    protected abstract int getLayoutId();

}
