package base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {
    private boolean isFirstVisible = true;
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstVisible && isVisibleToUser && view != null) {
            isFirstVisible = false;
            lazyLoad();
        }
    }

    protected abstract void lazyLoad();

    protected abstract void refresh();

    protected abstract int getLayoutId();

}
