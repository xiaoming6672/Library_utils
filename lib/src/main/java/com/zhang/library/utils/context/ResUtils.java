package com.zhang.library.utils.context;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * 资源相关工具类
 *
 * @author ZhangXiaoMing 2020-08-16 19:32 星期日
 */
public class ResUtils extends ContextUtils {

    /**
     * dp转px
     *
     * @param dp dp值
     */
    public static int dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param px 像素值
     */
    public static int px2dp(float px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param sp 字号
     */
    public static int sp2px(float sp) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    /** 获得屏幕宽度 */
    public static int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /** 获得屏幕高度 */
    public static int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /** 获得状态栏的高度 */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /** 返回activity的状态栏高度<br> 如果该activity的状态栏可见则返回状态栏高度，如果不可见则返回0 */
    public static int getActivityStatusBarHeight(Context context) {
        if (context instanceof Activity) {
            if (isStatusBarVisible((Activity) context)) {
                return getStatusBarHeight();
            } else {
                return 0;
            }
        } else {
            return getStatusBarHeight();
        }
    }

    /** 状态栏是否可见 */
    public static boolean isStatusBarVisible(Activity activity) {
        if (activity == null) {
            return false;
        }
        return ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0);
    }

    /**
     * 获得drawable图片文件名对应的资源id
     *
     * @param drawableName 资源id名字
     */
    public static int getIdentifierForDrawable(String drawableName) {
        try {
            return getResources().getIdentifier(drawableName, "drawable",
                    get().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获得mipmap图片文件名对应的资源id
     *
     * @param mipmapName 资源id名字
     */
    public static int getIdentifierForMipMap(String mipmapName) {
        try {
            return getResources().getIdentifier(mipmapName, "mipmap",
                    get().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
