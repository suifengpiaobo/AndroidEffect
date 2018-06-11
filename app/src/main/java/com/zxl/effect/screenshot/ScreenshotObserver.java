package com.zxl.effect.screenshot;

import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

import java.io.File;

/**
 * Description
 * Created by zxl on 2018/6/11 下午12:32.
 * Email:444288256@qq.com
 */
public class ScreenshotObserver extends FileObserver {
    private static final String TAG = "ScreenshotObserver";
    private static final String PATH = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots/";

    private OnScreenshotTakenListener listener;
    private String lastTakenPath;

    public ScreenshotObserver(OnScreenshotTakenListener listener) {
        super(PATH, FileObserver.CLOSE_WRITE);
        this.listener = listener;
    }

    @Override
    public void onEvent(int event, String path) {
        if (path==null || event!=FileObserver.CLOSE_WRITE){
            //dont care
        }
        else if (lastTakenPath!=null && path.equalsIgnoreCase(lastTakenPath)){
            //had observer,ignore this
        }
        else {
            lastTakenPath = path;
            File file = new File(PATH+path);
            listener.onScreenshotTaken(Uri.fromFile(file));
            Log.i(TAG, "Send event to listener.");
        }
    }

    public void start() {
        super.startWatching();
    }

    public void stop() {
        super.stopWatching();
    }

    public interface OnScreenshotTakenListener {
        void onScreenshotTaken(Uri uri);
    }
}
