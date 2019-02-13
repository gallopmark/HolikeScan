// Generated code from Butter Knife. Do not modify!
package com.haolaike.hotlikescan.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.haolaike.hotlikescan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OutScanActivity_ViewBinding implements Unbinder {
  private OutScanActivity target;

  private View view2131296294;

  private View view2131296296;

  private View view2131296295;

  @UiThread
  public OutScanActivity_ViewBinding(OutScanActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OutScanActivity_ViewBinding(final OutScanActivity target, View source) {
    this.target = target;

    View view;
    target.etSacnCode = Utils.findRequiredViewAsType(source, R.id.tv_outwarehose_sacnCode, "field 'etSacnCode'", EditText.class);
    target.rv = Utils.findRequiredViewAsType(source, R.id.rv_outwarehose, "field 'rv'", RecyclerView.class);
    target.tvScanNum = Utils.findRequiredViewAsType(source, R.id.tv_inwarehose_scanNum, "field 'tvScanNum'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_outwarehose, "method 'onViewClicked'");
    view2131296294 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_skip, "method 'onViewClicked'");
    view2131296296 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_outwarehose_input, "method 'onViewClicked'");
    view2131296295 = view;
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
    OutScanActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etSacnCode = null;
    target.rv = null;
    target.tvScanNum = null;

    view2131296294.setOnClickListener(null);
    view2131296294 = null;
    view2131296296.setOnClickListener(null);
    view2131296296 = null;
    view2131296295.setOnClickListener(null);
    view2131296295 = null;
  }
}
