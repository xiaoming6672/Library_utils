package com.zhang.library.utils.context;

import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.zhang.library.utils.constant.ViewDirection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * View 工具类
 *
 * @author ZhangXiaoMing 2020-05-27 17:35 星期三
 */
public class ViewUtils extends ContextUtils {

    private ViewUtils() {
    }


    //<editor-fold desc="显示/隐藏">

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

    /**
     * 设置view的可见状态
     *
     * @param view       指定的view
     * @param visibility 可见状态
     *
     * @return true-view处于设置的状态
     */
    public static boolean setVisibility(View view, int visibility) {
        if (view == null)
            return false;

        if (visibility == View.VISIBLE ||
                visibility == View.INVISIBLE ||
                visibility == View.GONE) {
            if (view.getVisibility() != visibility)
                view.setVisibility(visibility);
            return true;
        } else {
            throw new IllegalArgumentException("visibility is Illegal");
        }
    }

    /**
     * 设置view在VISIBLE和GONE之间切换
     *
     * @param view 指定的view
     *
     * @return true-view处于VISIBLE
     */
    public static boolean toggleVisibleOrGone(View view) {
        if (view == null)
            return false;

        if (view.getVisibility() == View.VISIBLE) {
            if (view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
            return false;
        } else {
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
            return true;
        }
    }

    /**
     * 设置view在VISIBLE和INVISIBLE之间切换
     *
     * @param view 指定的view
     *
     * @return true-view处于VISIBLE
     */
    public static boolean toggleVisibleOrInvisible(View view) {
        if (view == null)
            return false;

        if (view.getVisibility() == View.VISIBLE) {
            if (view.getVisibility() != View.INVISIBLE)
                view.setVisibility(View.INVISIBLE);
            return false;
        } else {
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
            return true;
        }
    }
    //</editor-fold>


    //<editor-fold desc="宽高">

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
     * 设置view的宽度
     *
     * @param view  view
     * @param width 宽度
     */
    public static void setWidth(View view, int width) {
        final LayoutParams params = view.getLayoutParams();
        if (params != null) {
            if (params.width != width) {
                params.width = width;
                view.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置view的高度
     *
     * @param view   view
     * @param height 高度
     */
    public static void setHeight(View view, int height) {
        final LayoutParams params = view.getLayoutParams();
        if (params != null) {
            if (params.height != height) {
                params.height = height;
                view.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置view的宽度和高度
     *
     * @param view   指定的view
     * @param width  指定的宽度
     * @param height 指定的高度
     */
    public static boolean setSize(View view, int width, int height) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            boolean needSet = false;
            if (params.width != width) {
                params.width = width;
                needSet = true;
            }
            if (params.height != height) {
                params.height = height;
                needSet = true;
            }

            if (needSet)
                view.setLayoutParams(params);

            return true;
        }
        return false;
    }

    /**
     * 根据传入的宽度，获得按指定比例缩放后的高度
     *
     * @param scaleWidth  指定的比例宽度
     * @param scaleHeight 指定的比例高度
     * @param width       宽度
     */
    public static int getScaleHeight(int scaleWidth, int scaleHeight, int width) {
        if (scaleWidth == 0)
            return 0;

        return scaleHeight * width / scaleWidth;
    }

    /**
     * 根据传入的高度，获得按指定比例缩放后的宽度
     *
     * @param scaleWidth  指定的比例宽度
     * @param scaleHeight 指定的比例高度
     * @param height      高度
     */
    public static int getScaleWidth(int scaleWidth, int scaleHeight, int height) {
        if (scaleHeight == 0)
            return 0;

        return scaleWidth * height / scaleHeight;
    }

    /**
     * 设置view的宽度为WRAP_CONTENT
     *
     * @param view 指定的view
     */
    public static void setWidthWrapContent(View view) {
        setWidth(view, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置view的宽度为MATCH_PARENT
     *
     * @param view 指定的view
     */
    public static void setWidthMatchParent(View view) {
        setWidth(view, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置view的高度为WRAP_CONTENT
     *
     * @param view 指定的view
     */
    public static void setHeightWrapContent(View view) {
        setHeight(view, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置view的高度为MATCH_PARENT
     *
     * @param view 指定的view
     */
    public static void setHeightMatchParent(View view) {
        setHeight(view, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置view的宽度和weight，仅当view处于LinearLayout里面时有效
     *
     * @param view   指定的view
     * @param width  宽度
     * @param weight 权重
     */
    public static void setWidthWeight(View view, int width, float weight) {
        if (view == null)
            return;

        final ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p instanceof LinearLayout.LayoutParams) {
            final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) p;

            boolean needSet = false;
            if (params.width != width) {
                params.width = width;
                needSet = true;
            }
            if (params.weight != weight) {
                params.weight = weight;
                needSet = true;
            }

            if (needSet)
                view.setLayoutParams(params);
        }
    }

    /**
     * 设置view的高度和weight，仅当view处于LinearLayout里面时有效
     *
     * @param view   指定的view
     * @param height 高度
     * @param weight 权重
     */
    public static void setHeightWeight(View view, int height, float weight) {
        if (view == null)
            return;

        final ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p instanceof LinearLayout.LayoutParams) {
            final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) p;

            boolean needSet = false;
            if (params.height != height) {
                params.height = height;
                needSet = true;
            }
            if (params.weight != weight) {
                params.weight = weight;
                needSet = true;
            }

            if (needSet)
                view.setLayoutParams(params);
        }
    }

    /**
     * 测量文字的宽度
     *
     * @param textView 指定的TextView
     * @param content  文字内容
     */
    public static float measureText(TextView textView, String content) {
        if (textView == null || content == null)
            return 0;

        return textView.getPaint().measureText(content);
    }

    //</editor-fold>


    //<editor-fold desc="ViewGroup子控件操作">

    /**
     * 添加子控件
     *
     * @param parent 父控件
     * @param child  子控件
     */
    public static void addView(View parent, View child) {
        addView(parent, child, false);
    }

    /**
     * 添加子控件
     *
     * @param parent        父控件
     * @param child         子控件
     * @param removeAllView 是否移除其他的子控件
     */
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
     * 替换显示，其他所有子控件隐藏
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
    //</editor-fold>


    //<editor-fold desc="Margin">

    /**
     * 获得指定view的 MarginLayoutParams
     *
     * @param view 指定的view
     */
    public static MarginLayoutParams getMarginLayoutParams(View view) {
        if (view == null) {
            return null;
        } else {
            LayoutParams params = view.getLayoutParams();
            return params instanceof MarginLayoutParams ? (MarginLayoutParams) params : null;
        }
    }

    /**
     * 设置指定view的左偏移量
     *
     * @param view 指定的view
     * @param left 左偏移量
     */
    public static void setMarginLeft(View view, int left) {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null) {
            return;
        }
        if (params.leftMargin != left) {
            params.leftMargin = left;
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置指定view 的上偏移量
     *
     * @param view 指定的view
     * @param top  上偏移量
     */
    public static void setMarginTop(View view, int top) {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null) {
            return;
        }
        if (params.topMargin != top) {
            params.topMargin = top;
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置指定view的右偏移量
     *
     * @param view  指定的view
     * @param right 右偏移量
     */
    public static void setMarginRight(View view, int right) {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null) {
            return;
        }
        if (params.rightMargin != right) {
            params.rightMargin = right;
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置指定view的下偏移量
     *
     * @param view   指定的view
     * @param bottom 下偏移量
     */
    public static void setMarginBottom(View view, int bottom) {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null) {
            return;
        }
        if (params.bottomMargin != bottom) {
            params.bottomMargin = bottom;
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置指定view的四个方向的偏移量
     *
     * @param view   指定的view
     * @param margin 四个方向的偏移量
     */
    public static void setMargins(View view, int margin) {
        setMargins(view, margin, margin, margin, margin);
    }

    /**
     * 设置指定view 的四个方向的偏移量
     *
     * @param view   指定的view
     * @param left   左偏移量
     * @param top    上偏移量
     * @param right  右偏移量
     * @param bottom 下偏移量
     */
    public static void setMargins(View view, int left, int top, int right, int bottom) {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null) {
            return;
        }
        boolean needSet = false;

        if (params.leftMargin != left) {
            params.leftMargin = left;
            needSet = true;
        }

        if (params.topMargin != top) {
            params.topMargin = top;
            needSet = true;
        }

        if (params.rightMargin != right) {
            params.rightMargin = right;
            needSet = true;
        }

        if (params.bottomMargin != bottom) {
            params.bottomMargin = bottom;
            needSet = true;
        }

        if (needSet) {
            view.setLayoutParams(params);
        }
    }

    /**
     * 获取控件指定方向的Margin值
     *
     * @param view      控件
     * @param direction 方向
     */
    public static int getMarginValue(View view, @ViewDirection int direction) {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null)
            return 0;

        int value = 0;
        switch (direction) {
            case ViewDirection.LEFT:
                value = params.leftMargin;
                break;
            case ViewDirection.TOP:
                value = params.topMargin;
                break;
            case ViewDirection.RIGHT:
                value = params.rightMargin;
                break;
            case ViewDirection.BOTTOM:
                value = params.bottomMargin;
                break;
        }

        return value;
    }

    /**
     * 设置控件指定方向上的Margin值
     *
     * @param view      控件
     * @param value     新的值
     * @param direction 方向
     */
    public static void setMarginValue(View view, int value, @ViewDirection int direction) {
        if (view == null)
            return;

        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null)
            return;

        switch (direction) {
            case ViewDirection.LEFT:
                if (params.leftMargin != value)
                    params.leftMargin = value;
                break;
            case ViewDirection.TOP:
                if (params.topMargin != value)
                    params.topMargin = value;
                break;
            case ViewDirection.RIGHT:
                if (params.rightMargin != value)
                    params.rightMargin = value;
                break;
            case ViewDirection.BOTTOM:
                if (params.bottomMargin != value)
                    params.bottomMargin = value;
                break;
        }

        view.setLayoutParams(params);
    }

    //</editor-fold>


    //<editor-fold desc="Padding">

    /**
     * 设置view内部四个方向的填充
     *
     * @param view   指定的view
     * @param left   左填充
     * @param top    上填充
     * @param right  右填充
     * @param bottom 下填充
     */
    public void setPaddings(View view, int left, int top, int right, int bottom) {
        if (view == null) {
            return;
        }

        view.setPadding(left, top, right, bottom);
    }

    /**
     * 设置view的内部四个方向的填充
     *
     * @param view    指定的view
     * @param padding 内部填充
     */
    public void setPadding(View view, int padding) {
        setPaddings(view, padding, padding, padding, padding);
    }

    /**
     * 设置view的内部左填充
     *
     * @param view 指定的view
     * @param left 左填充
     */
    public void setPaddingLeft(View view, int left) {
        if (view == null) {
            return;
        }

        view.setPadding(left, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 设置view的内部上填充
     *
     * @param view 指定的view
     * @param top  上填充
     */
    public void setPaddingTop(View view, int top) {
        if (view == null) {
            return;
        }

        view.setPadding(view.getPaddingLeft(), top, view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 设置view的内部右填充
     *
     * @param view  指定的view
     * @param right 右填充
     */
    public void setPaddingRight(View view, int right) {
        if (view == null) {
            return;
        }

        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), right, view.getPaddingBottom());
    }

    /**
     * 设置view的内部下填充
     *
     * @param view   指定的view
     * @param bottom 下填充
     */
    public void setPaddingBottom(View view, int bottom) {
        if (view == null) {
            return;
        }

        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), bottom);
    }
    //</editor-fold>


    //<editor-fold desc="RelativeLayout规则">

    /**
     * 当view的父布局是RelativeLayout的时候，设置view的布局规则
     *
     * @param view     指定的view
     * @param anchorId 目标id
     * @param rules    布局规则
     */
    public static void addRule(View view, int anchorId, Integer... rules) {
        if (view == null || rules == null)
            return;

        final ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p instanceof RelativeLayout.LayoutParams) {
            final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) p;

            for (Integer item : rules) {
                if (anchorId != 0) {
                    params.addRule(item, anchorId);
                } else {
                    params.addRule(item);
                }
            }
            view.setLayoutParams(params);
        }
    }

    /**
     * 当view的父布局是RelativeLayout的时候，移除view的布局规则
     *
     * @param view  指定的view
     * @param rules 要移除的布局规则
     */
    @TargetApi(17)
    public static void removeRule(View view, Integer... rules) {
        if (view == null || rules == null)
            return;

        final ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p instanceof RelativeLayout.LayoutParams) {
            final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) p;
            for (Integer item : rules) {
                params.removeRule(item);
            }
            view.setLayoutParams(params);
        }
    }
    //</editor-fold>


    //<editor-fold desc="控件坐标点">

    /**
     * 获取View的中心横坐标
     *
     * @param view 指定的view
     */
    public static float getViewCenterX(View view) {
        if (view == null) {
            return 0;
        }

        return view.getX() + 1F * getWidth(view) / 2;
    }

    /**
     * 获取View的中心纵坐标
     *
     * @param view 指定的view
     */
    public static float getViewCenterY(View view) {
        if (view == null) {
            return 0;
        }

        return view.getY() + 1F * getHeight(view) / 2 + ResUtils.getStatusBarHeight();
    }

    /** 获取控件在屏幕中的横向中心点 */
    public static int getViewCenterXOnScreen(View view) {
        int[] location = new int[2];

        view.getLocationOnScreen(location);
        return location[0] + view.getWidth() / 2;
    }

    /** 获取控件在屏幕中的纵向中心点 */
    public static int getViewCenterYOnScreen(View view) {
        int[] location = new int[2];

        view.getLocationOnScreen(location);
        return location[1] + view.getHeight() / 2;
    }

    /**
     * {@link #isViewUnder(View, int, int, int[])}
     *
     * @param view  指定的view
     * @param event 触摸点
     */
    public static boolean isViewUnder(View view, MotionEvent event) {
        if (view == null)
            return false;

        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();
        return isViewUnder(view, x, y, null);
    }

    /**
     * {@link #isViewUnder(View, int, int, int[])}
     *
     * @param view 制定的view
     * @param x    屏幕x坐标
     * @param y    屏幕y坐标
     */
    public static boolean isViewUnder(View view, int x, int y) {
        return isViewUnder(view, x, y, null);
    }

    /**
     * view是否在某个屏幕坐标下面
     *
     * @param view        指定的view
     * @param x           屏幕x坐标
     * @param y           屏幕y坐标
     * @param outLocation 用于接收view的x和y坐标的数组，可以为null
     */
    public static boolean isViewUnder(View view, int x, int y, int[] outLocation) {
        if (view == null)
            return false;

        if (!isAttached(view))
            return false;

        final int[] location = getLocationOnScreen(view, outLocation);
        final int left = location[0];
        final int top = location[1];
        final int right = left + view.getWidth();
        final int bottom = top + view.getHeight();

        return x >= left && x < right && y >= top && y < bottom;
    }

    /**
     * 获得view在屏幕上的坐标
     *
     * @param view        指定的view
     * @param outLocation 如果为null或者长度不等于2，内部会创建一个长度为2的数组返回
     */
    public static int[] getLocationOnScreen(View view, int[] outLocation) {
        if (outLocation == null || outLocation.length != 2)
            outLocation = new int[]{0, 0};

        if (view != null)
            view.getLocationOnScreen(outLocation);

        return outLocation;
    }

    //</editor-fold>


    /**
     * view是否被添加到界面上
     *
     * @param view 指定的view
     */
    public static boolean isAttached(View view) {
        if (view == null)
            return false;

        if (Build.VERSION.SDK_INT >= 19)
            return view.isAttachedToWindow();
        else
            return view.getWindowToken() != null;
    }


    /**
     * 获得view的截图
     *
     * @param view 指定的view
     */
    public static Bitmap createViewBitmap(View view) {
        if (view == null)
            return null;

        view.setDrawingCacheEnabled(true);
        final Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null)
            return null;

        final Bitmap result = Bitmap.createBitmap(drawingCache);
        view.destroyDrawingCache();
        return result;
    }


    /**
     * 获得view的镜像
     *
     * @param view 指定的view
     *
     * @return 镜像ImageView
     */
    public static ImageView getViewsImage(View view) {
        final Bitmap bitmap = createViewBitmap(view);
        if (bitmap == null)
            return null;

        final ImageView imageView = new ImageView(view.getContext());
        imageView.setImageBitmap(bitmap);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        imageView.setLayoutParams(params);
        return imageView;
    }

    /**
     * 包裹PopupWindow
     *
     * @param pop PopupWindow
     */
    public static void wrapperPopupWindow(PopupWindow pop) {
        if (pop == null)
            return;

        final ColorDrawable drawable = new ColorDrawable(0x00ffffff);
        pop.setBackgroundDrawable(drawable);
        pop.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
    }


    //<editor-fold desc="帧动画">

    /**
     * 开始动画Drawable
     *
     * @param drawable drawable图像资源
     */
    public static void startAnimationDrawable(Drawable drawable) {
        if (drawable instanceof AnimationDrawable) {
            final AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            if (!animationDrawable.isRunning())
                animationDrawable.start();
        }
    }

    /**
     * 停止动画Drawable
     *
     * @param drawable drawable图像资源
     */
    public static void stopAnimationDrawable(Drawable drawable) {
        stopAnimationDrawable(drawable, 0);
    }

    /**
     * 停止动画Drawable
     *
     * @param drawable  drawable图像资源
     * @param stopIndex 要停止在第几帧
     */
    public static void stopAnimationDrawable(Drawable drawable, int stopIndex) {
        if (drawable instanceof AnimationDrawable) {
            final AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            animationDrawable.stop();
            animationDrawable.selectDrawable(stopIndex);
        }
    }
    //</editor-fold>

    /**
     * 重置view
     *
     * @param view 指定的view
     */
    public static void resetView(View view) {
        if (view == null)
            return;

        view.setAlpha(1.0f);
        view.setRotation(0.0f);
        view.setRotationX(0.0f);
        view.setRotationY(0.0f);
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
    }


    //<editor-fold desc="StatusBar">

    /**
     * 设置状态栏背景透明
     *
     * @param activity 当前Activity界面
     */
    public static void setStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            window.setAttributes(attributes);
        }
    }

    /**
     * 设置状态栏文字颜色
     *
     * @param activity 页面
     * @param isDark   是否黑色字色
     */
    public static void setStatusBarTextColor(Activity activity, boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            int option = decorView.getSystemUiVisibility();
            if (isDark) {
                decorView.setSystemUiVisibility(option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(option & (~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
            }

            setMIUIStatusBarLightMode(activity, isDark);
            //LogUtils.test("option:" + decorView.getSystemUiVisibility());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            boolean isFlymeSet = setFlymeStatusBarLightMode(activity.getWindow(), isDark);

            if (!isFlymeSet)
                setMIUIStatusBarLightMode(activity, isDark);
        }
    }

    /**
     * 设置小米系统状态栏文字颜色
     *
     * @param activity 页面
     * @param dark     是否黑色
     */
    public static boolean setMIUIStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class<?> clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
//                    if(dark){
//                        activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                    }else {
//                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     *
     * @return boolean 成功执行返回true
     */
    public static boolean setFlymeStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 设置状态栏字体颜色
     * <br>SDK版本21（5.0）以上才生效
     *
     * @param activity 当前Activity
     * @param isDark   <b>true:</b>状态栏字体颜色白色<br>
     *                 <b>false:</b>状态栏字体颜色黑色
     */
    public static void setStatusBarDark(Activity activity, boolean isDark) {
        int flag = activity.getWindow().getDecorView().getSystemUiVisibility();
        if (isDark) {
            flag = addFlag(flag, FLAG_LAYOUT_IN_SCREEN);
            flag = clearFlag(flag, FLAG_SECURE);
        } else {
            flag = addFlag(flag, FLAG_SECURE);
            flag = clearFlag(flag, FLAG_LAYOUT_IN_SCREEN);
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);
    }

    private static int addFlag(int original, int flag) {
        return original | flag;
    }

    private static int clearFlag(int original, int flag) {
        return original & ~flag;
    }

    /** 适应沉浸式状态栏，在顶部添加状态栏高度的paddingTop */
    public static void fitsSystemWindowsByPaddingTop(View view) {
        if (view == null) {
            return;
        }

        int statusBarHeight = ResUtils.getStatusBarHeight();
        int left = view.getPaddingLeft();
        int top = view.getPaddingTop() + statusBarHeight;
        int right = view.getPaddingRight();
        int bottom = view.getPaddingBottom();

        view.setPadding(left, top, right, bottom);
    }

    /** 适应沉浸式状态栏，在顶部添加状态栏高度的marginTop */
    public static void fitsSystemWindowsByMarginTop(View view) {
        if (view == null)
            return;

        int top = 0;
        ViewGroup.MarginLayoutParams params = ViewUtils.getMarginLayoutParams(view);
        if (params != null)
            top += params.topMargin;

        int statusBarHeight = ResUtils.getStatusBarHeight();
        top += statusBarHeight;
        ViewUtils.setMarginTop(view, top);
    }


    /**
     * 是否全屏
     *
     * @param window 窗口
     */
    public static boolean isFullScreen(Window window) {
        if (window == null)
            return false;

        final WindowManager.LayoutParams params = window.getAttributes();
        if (params == null)
            return false;

        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }
    //</editor-fold>


    //<editor-fold desc="降低ViewPager2的灵敏度">

    /** 降低ViewPager2的灵敏度 */
    public static void desensitizeViewPager2(ViewPager2 viewPager2) {
        desensitizeViewPager2(viewPager2, 3);
    }

    /**
     * 降低ViewPager2的灵敏度
     *
     * @param viewPager2 ViewPager2控件
     * @param multiple   倍数
     */
    public static void desensitizeViewPager2(ViewPager2 viewPager2, int multiple) {
        try {
            Field mRecyclerViewField = viewPager2.getClass().getDeclaredField("mRecyclerView");
            mRecyclerViewField.setAccessible(true);
            RecyclerView rv = (RecyclerView) mRecyclerViewField.get(viewPager2);
            Field mTouchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop");
            mTouchSlopField.setAccessible(true);
            int touchSlop = (int) mTouchSlopField.get(rv);
            mTouchSlopField.set(rv, touchSlop * multiple);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>

}
