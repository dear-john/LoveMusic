package model;

/**
 * Created by 李君 on 2018/3/23.
 */

public interface MusicData<T> {
     void getMusicData(OnDataLoadFinished<T> loadFinished);
}
