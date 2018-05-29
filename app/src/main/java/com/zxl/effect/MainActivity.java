package com.zxl.effect;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.zxl.baselib.base.BaseActivity;
import com.zxl.baselib.base.BasePresenter;
import com.zxl.baselib.utils.ToastUtils;
import com.zxl.effect.circle.FriendCircleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private long exitTime;
    @BindView(R.id.friend_circle)
    TextView friendCircle;

    @Override
    protected int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected BasePresenter initPrestener() {
        return null;
    }

    @Override
    public void showError(String msg) {

    }

    @OnClick(R.id.friend_circle)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, FriendCircleActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
        }
        return true;
    }

    /**
     * 双击退出App
     */
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.show("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
