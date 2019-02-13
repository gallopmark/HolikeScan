// Generated code from Butter Knife. Do not modify!
package com.haolaike.hotlikescan.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.haolaike.hotlikescan.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DisInActivity_ViewBinding implements Unbinder {
  private DisInActivity target;

  @UiThread
  public DisInActivity_ViewBinding(DisInActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DisInActivity_ViewBinding(DisInActivity target, View source) {
    this.target = target;

    target.rv = Utils.findRequiredViewAsType(source, R.id.rv_disin, "field 'rv'", SwipeMenuRecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DisInActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv = null;
  }
}
