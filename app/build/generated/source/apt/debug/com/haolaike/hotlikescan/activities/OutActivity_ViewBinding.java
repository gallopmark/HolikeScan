// Generated code from Butter Knife. Do not modify!
package com.haolaike.hotlikescan.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.haolaike.hotlikescan.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OutActivity_ViewBinding implements Unbinder {
  private OutActivity target;

  private View view2131296291;

  @UiThread
  public OutActivity_ViewBinding(OutActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OutActivity_ViewBinding(final OutActivity target, View source) {
    this.target = target;

    View view;
    target.rv = Utils.findRequiredViewAsType(source, R.id.rv_out, "field 'rv'", SwipeMenuRecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btn_out_all, "method 'onViewClicked'");
    view2131296291 = view;
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
    OutActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv = null;

    view2131296291.setOnClickListener(null);
    view2131296291 = null;
  }
}
