// Generated code from Butter Knife. Do not modify!
package com.haolaike.hotlikescan.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.haolaike.hotlikescan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OutInfoActivity_ViewBinding implements Unbinder {
  private OutInfoActivity target;

  private View view2131296292;

  private View view2131296293;

  @UiThread
  public OutInfoActivity_ViewBinding(OutInfoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OutInfoActivity_ViewBinding(final OutInfoActivity target, View source) {
    this.target = target;

    View view;
    target.etCarrier = Utils.findRequiredViewAsType(source, R.id.et_out_info_carrier, "field 'etCarrier'", EditText.class);
    target.etCarNum = Utils.findRequiredViewAsType(source, R.id.et_out_info_carNum, "field 'etCarNum'", EditText.class);
    target.etLoadingCar = Utils.findRequiredViewAsType(source, R.id.et_out_info_loadingCar, "field 'etLoadingCar'", EditText.class);
    target.spReleaseWay = Utils.findRequiredViewAsType(source, R.id.sp_out_info_releaseWay, "field 'spReleaseWay'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.btn_out_info_clear, "method 'onViewClicked'");
    view2131296292 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_out_info_sure, "method 'onViewClicked'");
    view2131296293 = view;
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
    OutInfoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etCarrier = null;
    target.etCarNum = null;
    target.etLoadingCar = null;
    target.spReleaseWay = null;

    view2131296292.setOnClickListener(null);
    view2131296292 = null;
    view2131296293.setOnClickListener(null);
    view2131296293 = null;
  }
}
