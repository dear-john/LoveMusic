package bean;

/**
 * Created by 李君 on 2018/2/6.
 */

public class LocalMusic {
    private String singer;
    private String album;
    private String musicName;
    private String path;
    private int duration;
    private long size;
    private int musicOrder;

    public int getMusicOrder() {
        return musicOrder;
    }

    public void setMusicOrder(int musicOrder) {
        this.musicOrder = musicOrder;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
