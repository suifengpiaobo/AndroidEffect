package com.zxl.effect.circle;

import android.os.Bundle;

import com.zxl.baselib.base.BaseActivity;
import com.zxl.baselib.base.BasePresenter;
import com.zxl.effect.R;

public class FriendCircleActivity extends BaseActivity {

    @Override
    protected int getRootLayoutId() {
        return R.layout.activity_friend_circle;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenter initPrestener() {
        return null;
    }

    @Override
    public void showError(String msg) {

    }
}
