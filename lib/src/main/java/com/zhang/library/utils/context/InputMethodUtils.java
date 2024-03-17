package com.zhang.library.utils.context;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入法工具类
 *
 * @author ZhangXiaoMing 2023-10-15 18:19 周日
 */
public class InputMethodUtils extends ContextUtils {


    /**
     * 显示输入法
     *
     * @param view 控件
     */
    public static void showInputMethod(View view) {
        if (view == null)
            return;

        InputMethodManager manager = (InputMethodManager) get().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /** 切换输入法显示/隐藏状态 */
    public static void toggleInputMethod() {
        InputMethodManager manager = (InputMethodManager) get().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入法
     *
     * @param view 控件
     */
    public static void hideInputMethod(View view) {
        if (view == null)
            return;

        hideInputMethod(view.getWindowToken());
    }

    /**
     * 隐藏输入法
     *
     * @param activity 页面
     */
    public static void hideInputMethod(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null)
            return;

        hideInputMethod(activity.getCurrentFocus().getWindowToken());
    }

    /**
     * 隐藏输入法
     *
     * @param windowToken token
     */
    public static void hideInputMethod(IBinder windowToken) {
        if (windowToken == null)
            return;

        InputMethodManager manager = (InputMethodManager) get().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入法
     *
     * @param windowToken token
     * @param flag        隐藏的flag
     */
    public static void hideInputMethod(IBinder windowToken, int flag) {
        if (windowToken == null)
            return;

        InputMethodManager manager = (InputMethodManager) get().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(windowToken, flag);
    }

}
