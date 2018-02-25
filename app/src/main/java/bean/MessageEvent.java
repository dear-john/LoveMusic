package bean;

/**
 * Created by 李君 on 2018/2/6.
 */

public class MessageEvent {
    private int localMusicNumber;
    private boolean isMusicDataFinish = false;

    public boolean isMusicDataFinish() {
        return isMusicDataFinish;
    }

    public void setMusicDataFinish(boolean musicDataFinish) {
        isMusicDataFinish = musicDataFinish;
    }

    public int getLocalMusicNumber() {
        return localMusicNumber;
    }

    public void setLocalMusicNumber(int localMusicNumber) {
        this.localMusicNumber = localMusicNumber;
    }
}
