// Generated code from Butter Knife. Do not modify!
package com.haolaike.hotlikescan.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.haolaike.hotlikescan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131296289;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.etName = Utils.findRequiredViewAsType(source, R.id.et_login_name, "field 'etName'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.et_login_password, "field 'etPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_login_login, "field 'btnLogin' and method 'onViewClicked'");
    target.btnLogin = Utils.castView(view, R.id.btn_login_login, "field 'btnLogin'", TextView.class);
    view2131296289 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etName = null;
    target.etPassword = null;
    target.btnLogin = null;

    view2131296289.setOnClickListener(null);
    view2131296289 = null;
  }
}
