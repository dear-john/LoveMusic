package fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.spring_ballet.lovemusic.R;

import base.BaseFragment;
import utils.DialogUtil;
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
        ToastUtil.showShort(mContext, "load data");
    }
}
