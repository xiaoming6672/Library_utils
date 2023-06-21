package com.zhang.library.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Handler工具类
 *
 * @author ZhangXiaoMing 2020-05-26 17:29 星期二
 */
public class HandlerManager {

    private static final HandlerThread HANDLER_THREAD = new HandlerThread("HandlerThread");

    static {
        HANDLER_THREAD.start();

        MAIN_HANDLER = new Handler(Looper.getMainLooper());
        BACKGROUND_HANDLER = new Handler(HANDLER_THREAD.getLooper());
    }

    private static final Handler MAIN_HANDLER;
    private static final Handler BACKGROUND_HANDLER;

    private HandlerManager() {
    }

    /** 获得主线程Handler */
    public static Handler getMainHandler() {
        return MAIN_HANDLER;
    }

    /** 获得后台线程Handler */
    public static Handler getBackgroundHandler() {
        return BACKGROUND_HANDLER;
    }

    /**
     * 是否是主线程     *
     */
    public static boolean isMainHandler() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /** Ui线程执行 */
    public static void runOnUiThread(Runnable runnable) {
        if (runnable == null)
            return;

        if (isMainHandler())
            runnable.run();
        else
            MAIN_HANDLER.post(runnable);
    }
}
