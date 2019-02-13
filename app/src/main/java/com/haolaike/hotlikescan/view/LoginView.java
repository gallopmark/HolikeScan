package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;

/**
 * Created by holike on 2017/12/21.
 * 登录view
 */

public interface LoginView extends BaseView {
    void loginSuccess();

    void loginFailed(String failed);
}
