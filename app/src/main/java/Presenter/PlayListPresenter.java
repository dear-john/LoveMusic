package Presenter;

import java.util.LinkedList;
import java.util.List;

import app.AppConstants;
import bean.PlaylistItem;
import model.PlayListModel;

/**
 * Created by 李君 on 2018/4/2.
 */

public class PlayListPresenter implements PlayListContract.Presenter {

    private static PlayListContract.PlayListAdapterView sPlayListAdapterView;
    private PlayListContract.DialogView mDialogView;

    public PlayListPresenter(PlayListContract.PlayListAdapterView playListAdapterView, PlayListContract.DialogView dialogView) {
        sPlayListAdapterView = playListAdapterView;
        mDialogView = dialogView;
    }

    @Override
    public void initPlayMode() {
        if (mDialogView != null) {
            int mode = PlayListModel.getCurrentPlayMode();
            mDialogView.changePlayMode(AppConstants.PLAY_MODE_NAME[mode], AppConstants.PLAY_MODE_ICON[mode]);
        }
    }

    public static void addItemAsNext(PlaylistItem item) {
        List<PlaylistItem> list = new LinkedList<>();
        list.add(item);
        sPlayListAdapterView.refresh(list);
    }

    public static void addAll(List<PlaylistItem> list) {
        sPlayListAdapterView.refresh(list);
    }

    @Override
    public void refreshAdapter() {
//        if (sPlayListAdapterView != null)
//            sPlayListAdapterView.refresh();
    }

    @Override
    public void clearAdapter() {
        if (sPlayListAdapterView != null)
            sPlayListAdapterView.clear();
    }

    @Override
    public void changePlayMode() {
        if (mDialogView != null) {
            int mode = PlayListModel.getCurrentPlayMode();
            mDialogView.changePlayMode(AppConstants.PLAY_MODE_NAME[mode], AppConstants.PLAY_MODE_ICON[mode]);
        }
    }

    @Override
    public void destory() {
        if (sPlayListAdapterView != null) sPlayListAdapterView = null;
        if (mDialogView != null) mDialogView = null;
    }

}
