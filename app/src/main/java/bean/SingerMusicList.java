package bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by 李君 on 2018/2/23.
 */

public class SingerMusicList {

    private List<Song_list> songlist;
    private String songnums;
    private int havemore;
    private int error_code;

    @JSONField(name = "songlist")
    public List<Song_list> getSonglist() {
        return songlist;
    }

    @JSONField(name = "songlist")
    public void setSonglist(List<Song_list> songlist) {
        this.songlist = songlist;
    }

    public void setSongnums(String songnums) {
        this.songnums = songnums;
    }

    public String getSongnums() {
        return songnums;
    }

    public void setHavemore(int havemore) {
        this.havemore = havemore;
    }

    public int getHavemore() {
        return havemore;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code() {
        return error_code;
    }

}
