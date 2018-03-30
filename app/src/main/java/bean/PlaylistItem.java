package bean;

/**
 * Created by 李君 on 2018/3/26.
 */

public class PlaylistItem {
    private String songName;
    private String singerName;
    private String url;
    private String icon;

    public PlaylistItem(String songName, String singerName, String url, String icon) {
        this.songName = songName;
        this.singerName = singerName;
        this.url = url;
        this.icon = icon;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
