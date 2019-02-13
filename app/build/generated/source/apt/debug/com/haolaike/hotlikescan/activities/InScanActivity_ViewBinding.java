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

public class InScanActivity_ViewBinding implements Unbinder {
  private InScanActivity target;

  private View view2131296287;

  private View view2131296288;

  @UiThread
  public InScanActivity_ViewBinding(InScanActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InScanActivity_ViewBinding(final InScanActivity target, View source) {
    this.target = target;

    View view;
    target.etSacnCode = Utils.findRequiredViewAsType(source, R.id.tv_inwarehose_sacnCode, "field 'etSacnCode'", EditText.class);
    target.tvScanNum = Utils.findRequiredViewAsType(source, R.id.tv_inwarehose_scanNum, "field 'tvScanNum'", TextView.class);
    target.rv = Utils.findRequiredViewAsType(source, R.id.rv_inwarehose, "field 'rv'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btn_inwarehose_disInwarehose, "method 'onViewClicked'");
    view2131296287 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_inwarehose_input, "method 'onViewClicked'");
    view2131296288 = view;
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
    InScanActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etSacnCode = null;
    target.tvScanNum = null;
    target.rv = null;

    view2131296287.setOnClickListener(null);
    view2131296287 = null;
    view2131296288.setOnClickListener(null);
    view2131296288 = null;
  }
}
