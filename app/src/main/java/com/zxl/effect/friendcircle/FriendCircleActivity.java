package com.zxl.effect.friendcircle;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxl.baselib.base.BaseActivity;
import com.zxl.baselib.base.BasePresenter;
import com.zxl.baselib.utils.ToastUtils;
import com.zxl.baselib.utils.Utils;
import com.zxl.effect.R;
import com.zxl.effect.friendcircle.adapter.FriendCircleAdapter;
import com.zxl.effect.friendcircle.beans.FriendCircleBean;
import com.zxl.effect.friendcircle.widget.DivideLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.OnClick;
import ch.ielse.view.imagewatcher.ImageWatcher;


/**
 * 仿微信朋友圈
 */
public class FriendCircleActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        ImageWatcher.OnPictureLongPressListener, ImageWatcher.Loader {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swpie_refresh_layout)
    SwipeRefreshLayout swpieRefreshLayout;
    @BindView(R.id.image_watcher)
    ImageWatcher imageWatcher;

    private FriendCircleAdapter mAdapter;

    @Override
    protected int getRootLayoutId() {
        return R.layout.activity_friend_circle;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        swpieRefreshLayout.setOnRefreshListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(FriendCircleActivity.this).resumeRequests();
                } else {
                    Glide.with(FriendCircleActivity.this).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DivideLine());
        mAdapter = new FriendCircleAdapter(this, recyclerView, imageWatcher);
        recyclerView.setAdapter(mAdapter);
        imageWatcher.setTranslucentStatus(Utils.calcStatusBarHeight(this));
        imageWatcher.setErrorImageRes(R.mipmap.error_picture);
        imageWatcher.setOnPictureLongPressListener(this);
        imageWatcher.setLoader(this);

        swpieRefreshLayout.setRefreshing(true);
        loadData();
    }

    @Override
    protected BasePresenter initPrestener() {
        return null;
    }

    @Override
    public void showError(String msg) {

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        loadData();
    }

    public void loadData(){
        List<FriendCircleBean> friends = new ArrayList<>();
        FriendCircleBean friend;
        String url;
        for (int i = 0; i < 10; i++) {
            friend = new FriendCircleBean();
            friend.imageUrls = new ArrayList<>();
            for (int j=0;j<9;j++){
                Random random = new Random();
                url = Constants.IMAGE_URL[i*j];
                Log.i(TAG,"---position--->>>"+random.nextInt(50));
                Log.i(TAG,"---url--->>>"+url);
                friend.imageUrls.add(url);
            }
            friends.add(friend);
        }

        mAdapter.setData(friends);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swpieRefreshLayout.setRefreshing(false);
            }
        },2000);
    }

    @Override
    public void load(Context context, String url, ImageWatcher.LoadCallback lc) {
        Glide.with(context).asBitmap().load(url).into(new GlideSimpleTarget(lc));
    }

    /**
     * @param v   当前被按的ImageView
     * @param url 当前ImageView加载展示的图片url地址
     * @param pos 当前ImageView在展示组中的位置
     */
    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
        ToastUtils.show("Your Long Click "+pos);
    }
}
