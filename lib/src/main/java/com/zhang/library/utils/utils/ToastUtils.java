package com.zhang.library.utils.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast工具
 *
 * @author ZhangXiaoMing 2020-01-04 16:04 星期六
 */
public class ToastUtils extends ContextUtils {

    private ToastUtils() {
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(final CharSequence text, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showInternal(text, duration);
        } else {
            (new Handler(Looper.getMainLooper())).post(new Runnable() {
                public void run() {
                    showInternal(text, duration);
                }
            });
        }

    }

    private static void showInternal(CharSequence text, int duration) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(get(), text, duration).show();
        }
    }
}
