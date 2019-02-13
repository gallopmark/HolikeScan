package com.haolaike.hotlikescan.presenter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.model.BindShelfModel;
import com.haolaike.hotlikescan.utils.DensityUtil;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.BindShelfView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;

import java.util.List;

/**
 * Created by wqj on 2018/1/1.
 * 指定货架
 */

public class BindShelfPresenter extends BasePresenter<BindShelfView, BindShelfModel> {
    private CountDownTimer timer;

    @Override
    protected BindShelfModel getModel() {
        return new BindShelfModel();
    }

    /**
     * 指定货架
     *
     * @param code
     * @param shelfCodes
     */
    public void bindShelf(String code, List<String> shelfCodes) {
        getView().loading("正在绑定...");
        model.bindShelf(code, shelfCodes, new BindShelfModel.BindShelfListener() {
            @Override
            public void success(String result) {
                getView().BindShelSuccess(result);
            }

            @Override
            public void failed(String result) {
                getView().BindShelFailed(result);
            }
        });
    }

    /**
     * 侧滑删除菜单
     *
     * @param context
     * @return
     */
    public SwipeMenuCreator getSwipeMenuCreator(Context context) {
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                        .setHeight(DensityUtil.dp2px(46))
                        .setWidth(DensityUtil.dp2px(50))
                        .setBackgroundColorResource(R.color.textColor5)
                        .setTextColorResource(R.color.white)
                        .setText("删除");
                rightMenu.addMenuItem(deleteItem);
            }
        };
        return swipeMenuCreator;
    }

    /**
     * 侧滑删除
     *
     * @return
     */
    public SwipeMenuItemClickListener getSwipeMenuItemClickListener() {
        SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int position = menuBridge.getAdapterPosition();
                getView().delectItem(position);
            }
        };
        return menuItemClickListener;
    }

    /**
     * 是否扫描完成
     *
     * @param list
     * @return
     */
    public boolean isScanAll(List<String> list) {
        for (String text : list) {
            if (TextUtils.isEmpty(text)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 倒计时
     *
     * @param s
     */
    public void countdown(int s) {
        cancelCountdown();
        timer = new CountDownTimer(s * 1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                ToastUtils.showToast(millisUntilFinished / 1000 + "秒后自动绑定!");
            }

            @Override
            public void onFinish() {
                getView().countdownFinish();
            }
        };
        timer.start();
    }

    /**
     * 取消倒计时
     */
    public void cancelCountdown() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }
}
