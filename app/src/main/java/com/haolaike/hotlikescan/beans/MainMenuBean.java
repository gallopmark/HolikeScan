package com.haolaike.hotlikescan.beans;

import java.io.Serializable;

/**
 * Created by holike on 2017/12/21.
 * 首页菜单bean
 */

public class MainMenuBean implements Serializable {
    private String name;
    private int iconId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public MainMenuBean(String name, int iconId) {
        this.name = name;
        this.iconId = iconId;
    }
}
