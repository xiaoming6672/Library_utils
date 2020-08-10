package com.zhang.library.utils.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * 获取Context工具
 *
 * @author ZhangXiaoMing 2020-01-03 16:35 星期五
 */
public class ContextUtils {

    private static Context sContext;

    protected ContextUtils() {
    }

    /**
     * 设置Application Context
     *
     * @param context context
     */
    public static final void set(Context context) {
        if (context != null) {
            sContext = context.getApplicationContext();
        }
    }

    /** 获取Application Context */
    public static final Context get() {
        return sContext;
    }


    /** 返回应用程序的包资源实例 */
    public static Resources getResources() {
        return get().getResources();
    }

    /**
     * 获取颜色文件中配置的颜色
     *
     * @param resId 颜色资源id
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取文字文件中配置的文字
     *
     * @param resId 文字资源id
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字文件中配置的文字
     *
     * @param resId 文件资源id
     * @param args  组合参数
     */
    public static String getString(int resId, Object... args) {
        return getResources().getString(resId, args);
    }
}
