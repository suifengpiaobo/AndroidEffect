package com.zxl.baselib.utils;

import android.content.Context;

/**
 * Description
 * Created by zxl on 2018/5/29 下午1:02.
 * Email:444288256@qq.com
 */
public class ContextUtils {
    private static ContextUtils instance;
    private static Context context;

    public static ContextUtils getInstance() {
        if (instance == null) {
            synchronized (ContextUtils.class) {
                if (instance == null) {
                    instance = new ContextUtils();
                }
            }
        }
        return instance;
    }

    private ContextUtils() {}

    // No need, do not call this method !!
    public void setContext(Context context) {
        ContextUtils.context = context;
    }

    public Context getApplicationContext() {
        Context applicationContext = null;
        if (context != null) {
            applicationContext = context.getApplicationContext();
        }
        return applicationContext;
    }

    public Context getContext() {
        Context context = null;
        if (ContextUtils.context != null) {
            context = ContextUtils.context;
        }
        return context;
    }

    public void onDestroyContext() {
        context = null;
    }
}
