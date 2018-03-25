package model;

import java.util.List;

/**
 * Created by 李君 on 2018/3/23.
 */

public interface OnDataLoadFinished<T> {
    void onLoadFinished(List<T> list);
}
