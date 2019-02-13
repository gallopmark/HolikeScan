package com.haolaike.hotlikescan.presenter;

import android.app.Activity;
import android.content.Intent;

import com.haolaike.hotlikescan.activities.LoginActivity;
import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.model.MainModel;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.view.MainView;

/**
 * Created by holike on 2017/12/21.
 */

public class MainPresenter extends BasePresenter<MainView, MainModel> {
    @Override
    protected MainModel getModel() {
        return new MainModel();
    }

    /**
     * 判断是否已经登录
     *
     * @return
     */
    public boolean isLogin() {
        String token = SharedPreferencesUtils.getString(Constants.TOKEN, null);
        long expirationTime=SharedPreferencesUtils.getLong(Constants.EXPIRATIONTIME,0);
        if (token == null||System.currentTimeMillis()> expirationTime) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 退出登录
     *
     * @param activity
     */
    public void loginout(Activity activity) {
        SharedPreferencesUtils.saveString(Constants.USER, null);
        SharedPreferencesUtils.saveString(Constants.TOKEN, null);
        SharedPreferencesUtils.saveString(Constants.EXPIRATIONTIME, null);
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
