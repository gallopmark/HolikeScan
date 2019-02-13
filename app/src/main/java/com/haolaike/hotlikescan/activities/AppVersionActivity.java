package com.haolaike.hotlikescan.activities;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.AppVersionBean;
import com.haolaike.hotlikescan.dialog.SimpleDialog;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;
import com.haolaike.hotlikescan.utils.AppUtil;
import com.haolaike.hotlikescan.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * App版本信息
 */
public class AppVersionActivity extends com.haolaike.hotlikescan.base.BaseActivity {


    @BindView(R.id.tv_versionName)
    TextView tvVersionName;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;

    @Override
    protected void init() {
        tvVersionName.setText(String.format(getString(R.string.appversion_version), AppUtil.GetVersionName(this)));
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_app_version;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


    @OnClick(R.id.ll_update)
    public void onViewClicked() {
        checkAppversion(this);
    }

    /**
     * 版本检测
     *
     * @param context
     */
    public void checkAppversion(Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("version", AppUtil.GetVersionName(this));
        MyHttpClient.get(UrlPath.URL_CHECK_VERSION, params, new RequestCallBack<AppVersionBean>() {
            @Override
            public void onFailed(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String reason = jsonObject.getString("reason");
                    ToastUtils.showToast(reason);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(AppVersionBean result) {
                if (Integer.parseInt(result.getVersion()) > AppUtil.GetVersionCode(context)) {
                    new SimpleDialog(context).setDate(getString(R.string.appversion_dialog_update_title),
                            getString(R.string.appversion_dialog_update_contnet), getString(R.string.dialog_cancel), getString(R.string.dialog_sure)).show();
                }
            }


        });
    }
}
