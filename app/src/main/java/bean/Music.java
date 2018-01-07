package bean;


import java.util.List;

public class Music {

    private List<Song_list> song_list;
    private Billboard billboard;
    private int error_code;

    public void setSong_list(List<Song_list> song_list) {
        this.song_list = song_list;
    }

    public List<Song_list> getSong_list() {
        return song_list;
    }

    public void setBillboard(Billboard billboard) {
        this.billboard = billboard;
    }

    public Billboard getBillboard() {
        return billboard;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code() {
        return error_code;
    }


}
