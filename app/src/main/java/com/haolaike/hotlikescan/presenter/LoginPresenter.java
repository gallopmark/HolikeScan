package com.haolaike.hotlikescan.presenter;

import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.UserBean;
import com.haolaike.hotlikescan.model.LoginModel;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.view.LoginView;

/**
 * Created by holike on 2017/12/21.
 * 登录presenter
 */

public class LoginPresenter extends BasePresenter<LoginView, LoginModel> {
    @Override
    protected LoginModel getModel() {
        return new LoginModel();
    }

    /**
     * 登录
     *
     * @param name
     * @param password
     */
    public void login(String name, String password) {
        model.login(name, password, new LoginModel.LoginLintener() {
            @Override
            public void success(UserBean UserBean) {
                SharedPreferencesUtils.saveString(Constants.USER, name);
                SharedPreferencesUtils.saveString(Constants.TOKEN, UserBean.getToken());
                SharedPreferencesUtils.saveLong(Constants.EXPIRATIONTIME, UserBean.getExpirationTime());
                getView().loginSuccess();
            }

            @Override
            public void failed(String result) {
                getView().loginFailed(result);
            }
        });
    }
}
