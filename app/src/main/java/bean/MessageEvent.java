package bean;

/**
 * Created by 李君 on 2018/2/6.
 */

public class MessageEvent {
    private int localMusicNumber;
    private boolean isDataLoadFinish;

    public boolean isDataLoadFinish() {
        return isDataLoadFinish;
    }

    public void setDataLoadFinish(boolean dataLoadFinish) {
        isDataLoadFinish = dataLoadFinish;
    }

    public int getLocalMusicNumber() {
        return localMusicNumber;
    }

    public void setLocalMusicNumber(int localMusicNumber) {
        this.localMusicNumber = localMusicNumber;
    }
}
