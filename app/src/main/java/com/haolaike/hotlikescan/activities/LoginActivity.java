package com.haolaike.hotlikescan.activities;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BaseActivity;
import com.haolaike.hotlikescan.presenter.LoginPresenter;
import com.haolaike.hotlikescan.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ZaakMan on 2017/7/26.
 * 管理登录的Activity
 */

public class LoginActivity extends BaseActivity<LoginPresenter, LoginView> implements LoginView {
    @BindView(R.id.et_login_name)
    EditText etName;
    @BindView(R.id.et_login_password)
    EditText etPassword;
    @BindView(R.id.btn_login_login)
    TextView btnLogin;

    /**
     * 登录成功
     */
    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 登录失败
     *
     * @param failed
     */
    @Override
    public void loginFailed(String failed) {
        Toast.makeText(this, failed, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void init() {
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }


    @OnClick(R.id.btn_login_login)
    public void onViewClicked() {
        mPresenter.login(etName.getText().toString(), etPassword.getText().toString());
    }
}
