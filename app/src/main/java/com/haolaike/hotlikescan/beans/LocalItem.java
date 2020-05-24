package com.haolaike.hotlikescan.beans;

import android.support.annotation.Nullable;
import android.text.TextUtils;

public class LocalItem {
    public String bjbz;
    public String soOrderNo;
    public String childOrderNo;
    public String targetBztm;

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof LocalItem) {
            return TextUtils.equals(targetBztm, ((LocalItem) obj).targetBztm);
        } else {
            return false;
        }
    }
}
