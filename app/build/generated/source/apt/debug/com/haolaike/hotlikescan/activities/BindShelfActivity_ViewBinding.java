// Generated code from Butter Knife. Do not modify!
package com.haolaike.hotlikescan.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.haolaike.hotlikescan.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BindShelfActivity_ViewBinding implements Unbinder {
  private BindShelfActivity target;

  private View view2131296283;

  private View view2131296284;

  @UiThread
  public BindShelfActivity_ViewBinding(BindShelfActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BindShelfActivity_ViewBinding(final BindShelfActivity target, View source) {
    this.target = target;

    View view;
    target.tvComponent = Utils.findRequiredViewAsType(source, R.id.tv_bind_shelf_component, "field 'tvComponent'", TextView.class);
    target.rv = Utils.findRequiredViewAsType(source, R.id.rv_bind_shelf, "field 'rv'", SwipeMenuRecyclerView.class);
    target.cbAuto = Utils.findRequiredViewAsType(source, R.id.cb_bind_shelf_auto, "field 'cbAuto'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.btn_bind_shelf_add, "method 'onViewClicked'");
    view2131296283 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_bind_shelf_rescan, "method 'onViewClicked'");
    view2131296284 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    BindShelfActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvComponent = null;
    target.rv = null;
    target.cbAuto = null;

    view2131296283.setOnClickListener(null);
    view2131296283 = null;
    view2131296284.setOnClickListener(null);
    view2131296284 = null;
  }
}
