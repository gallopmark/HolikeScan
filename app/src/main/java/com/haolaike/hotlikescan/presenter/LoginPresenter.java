package com.haolaike.hotlikescan.presenter;

import android.support.annotation.NonNull;

import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.UserBean;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.model.LoginModel;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.view.LoginView;

import io.reactivex.disposables.Disposable;

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
     */
    public void login(String name, String password) {
        model.login(name, password, new RequestCallBack<UserBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                if (getView() != null) {
                    getView().onShowLoading();
                }
            }

            @Override
            public void onFailed(String result) {
                if (getView() != null) {
                    getView().onDismissLoading();
                    getView().loginFailed(result);
                }
            }

            @Override
            public void onSuccess(UserBean bean) {
                SharedPreferencesUtils.saveString(Constants.USER, name);
                SharedPreferencesUtils.saveString(Constants.TOKEN, bean.getToken());
                SharedPreferencesUtils.saveLong(Constants.EXPIRATIONTIME, bean.getExpirationTime());
                if (getView() != null) {
                    getView().onDismissLoading();
                    getView().loginSuccess();
                }
            }
        });
    }
}
