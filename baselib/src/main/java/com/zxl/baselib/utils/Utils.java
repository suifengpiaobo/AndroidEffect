package com.zxl.baselib.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Description
 * Created by zxl on 2018/5/29 下午3:09.
 * Email:444288256@qq.com
 */
public class Utils {
    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, ContextUtils.getInstance().getApplicationContext().getResources().getDisplayMetrics());
    }

    public static int getScreenWidth() {
        return ContextUtils.getInstance().getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
