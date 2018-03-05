package bean;

/**
 * Created by 李君 on 2018/3/5.
 */

public class NavItem {
    private String title;
    private int iconRes;

    public NavItem(String title, int iconRes) {
        this.title = title;
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
