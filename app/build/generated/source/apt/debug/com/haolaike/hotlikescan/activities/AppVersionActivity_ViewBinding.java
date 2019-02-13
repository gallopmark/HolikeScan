// Generated code from Butter Knife. Do not modify!
package com.haolaike.hotlikescan.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.haolaike.hotlikescan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppVersionActivity_ViewBinding implements Unbinder {
  private AppVersionActivity target;

  private View view2131296334;

  @UiThread
  public AppVersionActivity_ViewBinding(AppVersionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AppVersionActivity_ViewBinding(final AppVersionActivity target, View source) {
    this.target = target;

    View view;
    target.tvVersionName = Utils.findRequiredViewAsType(source, R.id.tv_versionName, "field 'tvVersionName'", TextView.class);
    target.tvUpdate = Utils.findRequiredViewAsType(source, R.id.tv_update, "field 'tvUpdate'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_update, "field 'llUpdate' and method 'onViewClicked'");
    target.llUpdate = Utils.castView(view, R.id.ll_update, "field 'llUpdate'", LinearLayout.class);
    view2131296334 = view;
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
    AppVersionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvVersionName = null;
    target.tvUpdate = null;
    target.llUpdate = null;

    view2131296334.setOnClickListener(null);
    view2131296334 = null;
  }
}
