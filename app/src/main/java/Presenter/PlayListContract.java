package Presenter;

import java.util.List;

import bean.PlaylistItem;

/**
 * Created by 李君 on 2018/4/2.
 */

public interface PlayListContract {

    interface PlayListAdapterView {

        void refresh(List<PlaylistItem> list);

        void clear();
    }

    interface DialogView {
        void changePlayMode(String modeName, int modeIcon);
    }

    interface Presenter {

        void initPlayMode();

        void refreshAdapter();

        void clearAdapter();

        void changePlayMode();

        void destory();
    }

}
