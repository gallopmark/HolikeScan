// Generated code from Butter Knife. Do not modify!
package com.haolaike.hotlikescan.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.haolaike.hotlikescan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131296290;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.tvUsername = Utils.findRequiredViewAsType(source, R.id.tv_main_username, "field 'tvUsername'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_main_exit, "field 'btnExit' and method 'onViewClicked'");
    target.btnExit = Utils.castView(view, R.id.btn_main_exit, "field 'btnExit'", TextView.class);
    view2131296290 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.rv = Utils.findRequiredViewAsType(source, R.id.rv_main, "field 'rv'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvUsername = null;
    target.btnExit = null;
    target.rv = null;

    view2131296290.setOnClickListener(null);
    view2131296290 = null;
  }
}
