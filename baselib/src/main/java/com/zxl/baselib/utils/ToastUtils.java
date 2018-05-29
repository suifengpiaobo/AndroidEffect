package com.zxl.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Description
 * Created by zxl on 2018/5/29 下午1:01.
 * Email:444288256@qq.com
 */
public class ToastUtils {
    private static Context context = ContextUtils.getInstance().getApplicationContext();
    private static Toast toast;

    public static void show(int resId) {
        show(context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(context.getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }


    public static void show(CharSequence text, int duration) {
        text = TextUtils.isEmpty(text == null ? "" : text.toString()) ? "请检查您的网络！"
                : text;
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void show(int resId, Object... args) {
        show(String.format(context.getResources().getString(resId), args),
                Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        show(String.format(context.getResources().getString(resId), args),
                duration);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    public static void showToastOnSubThread(@NonNull final Fragment fragment, @NonNull final String content) {
        fragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(content,Toast.LENGTH_LONG);
            }
        });
    }

    public static void showToastOnSubThread(@NonNull final Fragment fragment, @StringRes final int stringResId) {
        fragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(Toast.LENGTH_SHORT,stringResId);

            }
        });
    }

    public static void showToastOnSubThread(@NonNull final Activity activity, @NonNull final  String content) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(content);
            }
        });
    }

    public static void showToastOnSubThread(@NonNull final Activity activity, @StringRes final  int stringResId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(Toast.LENGTH_SHORT,stringResId);
            }
        });
    }
}
