package com.haolaike.hotlikescan.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.OutInfoBean;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.dialog.SimpleDialog;
import com.haolaike.hotlikescan.utils.CommentUtil;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.utils.ToastUtils;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 出库必填信息
 */
public class OutInfoActivity extends com.haolaike.hotlikescan.base.BaseActivity {

    @BindView(R.id.et_out_info_carrier)
    EditText etCarrier;
    @BindView(R.id.et_out_info_carNum)
    EditText etCarNum;
    @BindView(R.id.et_out_info_loadingCar)
    EditText etLoadingCar;
    @BindView(R.id.sp_out_info_releaseWay)
    Spinner spReleaseWay;

    private String[] releaseWays;

    @Override
    protected void init() {
        releaseWays = getResources().getStringArray(R.array.releaseWay);
        String outInfo = SharedPreferencesUtils.getString(Constants.OUTINFOBEAN, null);
        int outSize = PartsData.getInstance().getOutInfoMap().keySet().size();
        showInfo(new Gson().fromJson(outInfo, OutInfoBean.class));
        if (outSize > 0) {
            new SimpleDialog(this).setDate(getString(R.string.outinfo_dialog_title), getString(R.string.outinfo_dialog_contnet),
                    getString(R.string.dialog_cancel), getString(R.string.outinfo_dialog_continue))
                    .setListener(new SimpleDialog.ClickListener() {
                        @Override
                        public void left() {
                            PartsData.getInstance().deleteOut(PartsData.getInstance().getAllOutList());
                        }

                        @Override
                        public void right() {
                            startActivity(OutActivity.class);
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_out_info;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void scanData(String code) {
        getFocusView().setText(code);
    }

    private EditText getFocusView() {
        if (etCarrier.hasFocus()) return etCarrier;
        if (etCarNum.hasFocus()) return etCarNum;
        return etLoadingCar;
    }

    @OnClick({R.id.btn_out_info_clear, R.id.btn_out_info_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_out_info_clear:
                showInfo(null);
                break;
            case R.id.btn_out_info_sure:
                String zcyf = etCarrier.getText().toString().trim();//承运方
                String zcph = etCarNum.getText().toString().trim();//车牌号
                String zthfs = spReleaseWay.getSelectedItem().toString().trim();//提货方式
                String zzcbz = etLoadingCar.getText().toString().trim();//装车班组
                if (TextUtils.isEmpty(zcyf) || TextUtils.isEmpty(zcph) || TextUtils.isEmpty(zthfs) || TextUtils.isEmpty(zzcbz)) {
                    ToastUtils.showToast(getString(R.string.outinfo_perfect_info));
                } else if (!CommentUtil.MatchString(getString(R.string.car_num_exp), zcph)) {
                    ToastUtils.showToast(getString(R.string.outinfo_carnum_err));
                } else {
                    OutInfoBean outInfoBean = new OutInfoBean(zcyf, zthfs, zcph, zzcbz);
                    SharedPreferencesUtils.saveString(Constants.OUTINFOBEAN, outInfoBean.toString());
                    Intent intent = new Intent(this, OutScanActivity.class);
                    intent.putExtra("outinfo", outInfoBean);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    /**
     * 显示出库信息
     *
     * @param outInfoBean
     */
    public void showInfo(OutInfoBean outInfoBean) {
        etCarrier.setText(outInfoBean == null ? "" : outInfoBean.getZcyf());
        etCarNum.setText(outInfoBean == null ? "" : outInfoBean.getZcph());
        spReleaseWay.setSelection(outInfoBean == null ? 0 : Arrays.binarySearch(releaseWays, outInfoBean.getZthfs()));
        etLoadingCar.setText(outInfoBean == null ? "" : outInfoBean.getZzcbz());
    }
}
