package com.zxl.effect.screenshot;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zxl.baselib.base.BaseActivity;
import com.zxl.baselib.base.BasePresenter;
import com.zxl.baselib.utils.ToastUtils;
import com.zxl.effect.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ScreenShotActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    private static final int RC_CAMERA_AND_LOCATION = 100;
    ScreenShotListenManager manager;
    @BindView(R.id.shot_img)
    ImageView mShotImg;

    private RequestOptions mRequestOptions;

    private DrawableTransitionOptions mDrawableTransitionOptions;


    /**
     * 需要申请的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected int getRootLayoutId() {
        return R.layout.activity_screen_shot;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        this.mRequestOptions = new RequestOptions().centerCrop();
        this.mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
        manager = ScreenShotListenManager.newInstance(ScreenShotActivity.this);

        manager.setListener(new ScreenShotListenManager.OnScreenShotListener() {
            @Override
            public void onShot(String imagePath) {
                ToastUtils.show(imagePath);

                Glide.with(mContext).load(imagePath).apply(mRequestOptions).transition(mDrawableTransitionOptions).into(mShotImg);
            }
        });
    }

    @Override
    protected BasePresenter initPrestener() {
        return null;
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {

        if (EasyPermissions.hasPermissions(this, needPermissions)) {
            manager.startListen();
        } else {
            EasyPermissions.requestPermissions(this, "",
                    RC_CAMERA_AND_LOCATION, needPermissions);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        manager.stopListen();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
