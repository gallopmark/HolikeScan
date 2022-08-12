package com.haolaike.hotlikescan.activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BaseActivity;
import com.haolaike.hotlikescan.beans.MainMenuBean;
import com.haolaike.hotlikescan.dialog.SimpleDialog;
import com.haolaike.hotlikescan.presenter.MainPresenter;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.DateUtils;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.view.MainView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.tv_main_username)
    TextView tvUsername;
    @BindView(R.id.btn_main_exit)
    TextView btnExit;
    @BindView(R.id.rv_main)
    RecyclerView rv;

    private final int[] iconIds = {R.drawable.storage, R.drawable.storage, R.drawable.release, R.drawable.ic_onshelf_scanning, R.drawable.app_info, R.drawable.bind_rack};
    private final List<MainMenuBean> beanList = new ArrayList<>();

    @Override
    protected void init() {
        if (mPresenter.isLogin()) {
            String[] names = getResources().getStringArray(R.array.main_item);
            tvUsername.setText(SharedPreferencesUtils.getString(Constants.USER, ""));
            rv.setLayoutManager(new GridLayoutManager(this, 2));
            rv.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.bg_divider)));
            for (int i = 0, size = names.length; i < size; i++) {
                MainMenuBean bean = new MainMenuBean(i, names[i], iconIds[i]);
                beanList.add(bean);
            }
            setList(this, rv, beanList);
        } else {
            login();
        }
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }

    @OnClick(R.id.btn_main_exit)
    public void onViewClicked() {
        mPresenter.loginout(this);
    }

    public void setList(final Context context, RecyclerView recyclerView, List<MainMenuBean> list) {
        recyclerView.setAdapter(new CommonAdapter<MainMenuBean>(context, R.layout.item_rv_main_menu, list) {
            @Override
            protected void convert(ViewHolder holder, MainMenuBean bean, int position) {
                TextView tvName = holder.getView(R.id.tv_item_main_menu);
                ImageView ivImg = holder.getView(R.id.iv_item_main_menu);
                tvName.setText(bean.name);
                ivImg.setImageResource(bean.iconId);
                holder.getConvertView().setOnClickListener(v -> {
                    switch (bean.type) {
                        case 0:
                            startActivity(InScan2Activity.class);
                            break;
                        case 1:
                            startActivity(InScanActivity.class);
                            break;
                        case 2:
                            startActivity(OutInfoActivity.class);
                            break;
                        case 3:
                            startActivity(OnShelfScanActivity.class);
                            break;
                        case 4:
                            startActivity(AppVersionActivity.class);
                            break;
                        case 5:
                            startActivity(BindShelfActivity.class);
                            break;
                    }
                });
            }
        });
    }

    /**
     * 去登录
     */
    @Override
    public void login() {
        startActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        new SimpleDialog(this).setDate(getString(R.string.dialog_exit), getString(R.string.dialog_sure_exit),
                getString(R.string.dialog_cancel), getString(R.string.dialog_sure)).setListener(new SimpleDialog.ClickListener() {
            @Override
            public void left() {
            }

            @Override
            public void right() {
                finish();
            }
        }).show();
    }
}
