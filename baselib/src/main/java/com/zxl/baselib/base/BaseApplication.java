package com.zxl.baselib.base;

import android.support.multidex.MultiDexApplication;

import com.zxl.baselib.utils.ContextUtils;
import com.zxl.baselib.utils.ToastUtils;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Description
 * Created by zxl on 2018/5/29 下午12:55.
 * Email:444288256@qq.com
 */
public class BaseApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.getInstance().setContext(this);
        BGASwipeBackHelper.init(this, null);
    }
}
