package com.zxl.baselib.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zxl.baselib.R;
import com.zxl.baselib.utils.MyDialog;
import com.zxl.baselib.utils.ScreenShotListenManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Description
 * Created by zxl on 2018/5/29 下午12:15.
 * Email:444288256@qq.com
 */
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BGASwipeBackHelper.Delegate,BaseView {
    protected final String TAG = getClass().getSimpleName();
    protected Context mContext;

    protected T mPresenter;

    protected BGASwipeBackHelper mSwipeBackHelper;
    protected boolean existActivityWithAnimation = true;

    private Unbinder bind;

    private ScreenShotListenManager screenShotListenManager;
    private boolean isHasScreenShotListener = false;
    private String path;

    private ImageView screenShotIv;
    private ProgressBar progressBar;
    private Handler mHandler = new Handler();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(getRootLayoutId());

        bind = ButterKnife.bind(this);

        mPresenter = initPrestener();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        init(savedInstanceState);

        screenShotListenManager = ScreenShotListenManager.newInstance(this);

    }

    protected abstract @LayoutRes
    int getRootLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract T initPrestener();

    public T getPresenter() {
        return mPresenter;
    }
    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish(){
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {

    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startScreenShotListen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScreenShotListen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (isSupportSwipeBack()){
            if (existActivityWithAnimation) {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }
    }

    /**
     * 监听
     */
    private void startScreenShotListen() {
        if (!isHasScreenShotListener && screenShotListenManager != null) {
            screenShotListenManager.setListener(new ScreenShotListenManager.OnScreenShotListener() {
                @Override
                public void onShot(String imagePath) {

                    path = imagePath;
                    Log.d("msg", "BaseActivity -> onShot: " + "获得截图路径：" + imagePath);

                    MyDialog ksDialog = MyDialog.getInstance()
                            .init(BaseActivity.this, R.layout.dialog_layout)
                            .setCancelButton("取消", null)
                            .setPositiveButton("生成新图片", new MyDialog.OnClickListener() {
                                @Override
                                public void OnClick(View view) {
                                    Bitmap screenShotBitmap = screenShotListenManager.createScreenShotBitmap(mContext,getRootLayoutId(), path);

                                    // 此处只要分享这个合成的Bitmap图片就行了
                                    // 为了演示，故写下面代码
                                    screenShotIv.setImageBitmap(screenShotBitmap);
                                }
                            });

                    screenShotIv = (ImageView) ksDialog.getView(R.id.iv);
                    progressBar = (ProgressBar) ksDialog.getView(R.id.avLoad);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            Glide.with(mContext).load(path).into(screenShotIv);

                        }
                    }, 600);
                }
            });
            screenShotListenManager.startListen();
            isHasScreenShotListener = true;
        }
    }

    /**
     * 停止监听
     */
    private void stopScreenShotListen() {
        if (isHasScreenShotListener && screenShotListenManager != null) {
            screenShotListenManager.stopListen();
            isHasScreenShotListener = false;
        }
    }
}
