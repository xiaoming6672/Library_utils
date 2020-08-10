package com.zhang.library.utils.utils;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

/**
 * View 工具类
 *
 * @author ZhangXiaoMing 2020-05-27 17:35 星期三
 */
public class ViewUtils extends ContextUtils {

    private ViewUtils() {
    }

    /**
     * 设置View是否显示
     *
     * @param view view控件
     * @param show 是否显示
     */
    public static void setViewVisibleOrGone(View view, boolean show) {
        if (view == null) {
            return;
        }
        if (show)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }

    /**
     * 设置View是否显示
     *
     * @param view view控件
     * @param show 是否显示
     */
    public static void setViewVisibleOrInVisible(View view, boolean show) {
        if (view == null) {
            return;
        }
        if (show)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.INVISIBLE);
    }

    /** 返回Resources资源对象 */
    public static Resources getResources() {
        return get().getResources();
    }

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

    /** 获得屏幕宽度 */
    public static int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /** 获得屏幕高度 */
    public static int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得view的宽度
     *
     * @param view view
     */
    public static int getWidth(View view) {
        int width = 0;
        width = view.getWidth();
        if (width <= 0) {
            if (view.getLayoutParams() != null) {
                width = view.getLayoutParams().width;
            }
        }
        if (width <= 0) {
            measureView(view);
            width = view.getMeasuredWidth();
        }
        return width;
    }

    /**
     * 设置view的宽度
     *
     * @param view  view
     * @param width 宽度
     *
     * @return true-view的高度和指定的高度的一致
     */
    public static boolean setWidth(View view, int width) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            if (params.width != width) {
                params.width = width;
                view.setLayoutParams(params);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置view的高度
     *
     * @param view   view
     * @param height 高度
     *
     * @return true-view的高度和指定的高度的一致
     */
    public static boolean setHeight(View view, int height) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            if (params.height != height) {
                params.height = height;
                view.setLayoutParams(params);
            }
            return true;
        }
        return false;
    }

    /**
     * 获得view的高度
     *
     * @param view view
     */
    public static int getHeight(View view) {
        int height = 0;
        height = view.getHeight();
        if (height <= 0) {
            if (view.getLayoutParams() != null) {
                height = view.getLayoutParams().height;
            }
        }
        if (height <= 0) {
            measureView(view);
            height = view.getMeasuredHeight();
        }
        return height;
    }


    /**
     * 测量view，测量后，可以获得view的测量宽高
     *
     * @param view view
     */
    public static void measureView(View view) {
        if (view == null)
            return;

        final int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
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

    /**
     * 添加子控件
     *
     * @param parent 父控件
     * @param child  子控件
     */
    public static void addView(View parent, View child) {
        addView(parent, child, false);
    }

    public static void addView(View parent, View child, final boolean removeAllView) {
        if (parent == null || child == null)
            return;
        if (!(parent instanceof ViewGroup))
            throw new IllegalArgumentException("parent should be ViewGroup!!");

        final ViewGroup viewGroup = (ViewGroup) parent;
        if (child.getParent() != parent) {
            if (removeAllView) {
                viewGroup.removeAllViews();
            }

            removeView(child);
            viewGroup.addView(child);
        } else {
            if (removeAllView) {
                for (int index = viewGroup.getChildCount() - 1; index >= 0; index--) {
                    View childView = viewGroup.getChildAt(index);
                    if (childView != child) {
                        viewGroup.removeView(childView);
                    }
                }
            }
        }
    }

    /**
     * 替换显示，移除其他所有的子控件
     *
     * @param parent 父控件
     * @param child  子控件
     */
    public static void replaceView(View parent, View child) {
        addView(parent, child, true);
    }

    /**
     * 替换显示，其他所有字控件隐藏
     *
     * @param parent 父控件
     * @param child  子控件
     */
    public static void toggleView(ViewGroup parent, View child) {
        if (parent == null || child == null) {
            return;
        }

        if (child.getParent() != parent) {
            removeView(child);
            parent.addView(child);
        }

        for (int index = 0; index < parent.getChildCount(); index++) {
            final View childView = parent.getChildAt(index);
            if (childView != child) {
                childView.setVisibility(View.GONE);
            } else {
                childView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 移除view所在父控件的所有字控件
     *
     * @param view view
     */
    public static void removeView(View view) {
        try {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeAllViews();
        } catch (Exception ignore) {
        }
    }
}
