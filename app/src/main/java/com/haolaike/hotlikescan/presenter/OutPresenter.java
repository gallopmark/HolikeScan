package com.haolaike.hotlikescan.presenter;

import android.content.Context;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.OutInfoBean;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.beans.SubmitResultBean;
import com.haolaike.hotlikescan.model.OutModel;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.DensityUtil;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.view.OutView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wqj on 2018/1/1.
 * 出库
 */

public class OutPresenter extends BasePresenter<OutView, OutModel> {
    @Override
    protected OutModel getModel() {
        return new OutModel();
    }

    /**
     * 出库
     *
     * @param list
     * @param outInfoBean
     */
    public void out(List<PartsBean> list, OutInfoBean outInfoBean) {
        if (list != null && list.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (PartsBean bean : list) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("bztm", bean.getBztm());
                    jsonObject.put("zcksj", bean.getTime());
                    jsonObject.put("zckr", SharedPreferencesUtils.getString(Constants.USER, "")); //入库操作人
                    jsonObject.put("zcyf", outInfoBean.getZcyf());
                    jsonObject.put("zcph", outInfoBean.getZcph());
                    jsonObject.put("zthfs", outInfoBean.getZthfs());
                    jsonObject.put("zzcbz", outInfoBean.getZzcbz());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            JSONObject json = new JSONObject();
            try {
                json.put("releasePackageList", jsonArray);
                json.put("token", SharedPreferencesUtils.getString(Constants.TOKEN, ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            model.out(list.get(0).getBztm(), json.toString(), new OutModel.OutListener() {
                @Override
                public void success(JSONObject result) {
                    getView().outSuccess();
                }

                @Override
                public void failed(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        result = dataObject.optString("emessage");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getView().outFailed(result);
                }
            });
        } else {
            getView().outFailed("没有可以出库列表");
        }
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
                        .setHeight(DensityUtil.dp2px(50))
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
}
