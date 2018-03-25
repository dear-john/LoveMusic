package Presenter;

import java.util.List;

/**
 * Created by 李君 on 2018/3/25.
 */

public interface MusicContract {

    interface View<T> {
        void refreshView(List<T> list);
    }

    interface Presenter {

        void loadData();

        void destory();
    }

}
