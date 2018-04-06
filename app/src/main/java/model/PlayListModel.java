package model;

import java.util.List;

import app.AppConstants;
import bean.PlaylistItem;


/**
 * Created by 李君 on 2018/4/2.
 */

public class PlayListModel {

    private static int currentPlayMode = -1;

    public static List<PlaylistItem> getPlayListData() {
        //从本地存储读取数据
        return null;
    }

    public static int getCurrentPlayMode() {
        return ++currentPlayMode % AppConstants.PLAY_MODE_NUMBER;
    }

}
