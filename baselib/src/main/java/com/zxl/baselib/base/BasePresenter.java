package com.zxl.baselib.base;

/**
 * Description
 * Created by zxl on 2018/5/29 下午12:14.
 * Email:444288256@qq.com
 */
public abstract class BasePresenter<V extends BaseView> {
    private V mView;

    public void attachView(V mView) {
        this.mView = mView;
    }

    public void detachView() {
        mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public V getMvpView() {
        return mView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewViewNotAttachedException();
    }

    public static class MvpViewViewNotAttachedException extends RuntimeException {
        public MvpViewViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
