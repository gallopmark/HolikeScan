package com.haolaike.hotlikescan.beans;

import java.io.Serializable;

/**
 * Created by holike on 2017/12/21.
 * 首页菜单bean
 */

public class MainMenuBean {
    public int type;
    public String name;
    public int iconId;

    public MainMenuBean(int type, String name, int iconId) {
        this.type = type;
        this.name = name;
        this.iconId = iconId;
    }
}
