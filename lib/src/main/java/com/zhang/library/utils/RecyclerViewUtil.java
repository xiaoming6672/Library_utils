package com.zhang.library.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView使用的工具类
 *
 * @author ZhangXiaoMing 2021-12-18 10:18 星期六
 */
public class RecyclerViewUtil {


    /** 是否是线性布局 */
    public static boolean isLinearLayoutManager(@NonNull RecyclerView parent) {
        return (parent.getLayoutManager() != null && parent.getLayoutManager() instanceof LinearLayoutManager)
                && !isGridLayoutManager(parent);
    }

    /** 是否是表格布局 */
    public static boolean isGridLayoutManager(@NonNull RecyclerView parent) {
        return parent.getLayoutManager() != null && parent.getLayoutManager() instanceof GridLayoutManager;
    }

    /** 是否是瀑布流布局 */
    public static boolean isStaggeredGridLayoutManager(@NonNull RecyclerView parent) {
        return parent.getLayoutManager() != null && parent.getLayoutManager() instanceof StaggeredGridLayoutManager;
    }

    /**
     * 判断是否是同一个{@link LayoutManager}
     *
     * @param parent 列表
     * @param clazz  LayoutManager类
     */
    public static boolean isSameLayoutManager(@NonNull RecyclerView parent, @NonNull Class<? extends LayoutManager> clazz) {
        LayoutManager manager = parent.getLayoutManager();
        if (manager == null)
            return false;

        return TextUtils.equals(manager.getClass().getName(), clazz.getName());
    }

    /** 获取总的列数 */
    public static int getTotalColumns(@NonNull RecyclerView parent) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null)
            return 0;

        final int childCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();

        if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;

            return manager.getOrientation() == RecyclerView.VERTICAL ? 1 : childCount;
        }

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            final int spanCount = manager.getSpanCount();

            return manager.getOrientation() == RecyclerView.VERTICAL ?
                    spanCount : childCount / spanCount + (childCount % spanCount == 0 ? 0 : 1);
        }

        //瀑布流视具体情况，此处只计算垂直方向的列表
        if (isStaggeredGridLayoutManager(parent)) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;

            if (manager.getOrientation() == StaggeredGridLayoutManager.VERTICAL)
                return manager.getSpanCount();
        }

        return 0;
    }

    /** 获取总的行数 */
    public static int getTotalRows(@NonNull RecyclerView parent) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null)
            return 0;

        final int childCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();

        if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;

            return manager.getOrientation() == RecyclerView.VERTICAL ? childCount : 1;
        }

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            final int spanCount = manager.getSpanCount();

            return manager.getOrientation() == RecyclerView.VERTICAL ?
                    childCount / spanCount + (childCount % spanCount == 0 ? 0 : 1) : spanCount;
        }

        //瀑布流视具体情况，此处只计算水平方向的列表
        if (isStaggeredGridLayoutManager(parent)) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;

            if (manager.getOrientation() == StaggeredGridLayoutManager.HORIZONTAL) {
                return manager.getSpanCount();
            }
        }

        return 0;
    }

    /**
     * 判断当前item是否是第一行（仅当列表没有添加Header的时候，如果有添加Header请另行判断）
     *
     * @param parent   RecyclerView列表
     * @param position 当前item位置
     */
    public static boolean isFirstRow(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return false;

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            final int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL)
                return position < spanCount;
            else
                return position % spanCount == 0;
        } else if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.HORIZONTAL || position == 0;
        }

        return false;
    }

    /**
     * 判断当前item是否是最后一行（仅当列表没有添加Footer的时候，如果有添加Footer请另行判断）
     *
     * @param parent   RecyclerView列表
     * @param position 当前item位置
     */
    public static boolean isLastRow(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return false;

        final int childCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            final int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL) {

                int result = childCount % spanCount;
                if (result == 0)
                    return position >= childCount - spanCount;
                else
                    return position >= childCount - result;
            } else {
                return (position + 1) % spanCount == 0;
            }
        } else if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.HORIZONTAL      //横向
                    || position == childCount - 1;                          //纵向
        }

        return false;
    }

    /**
     * 判断当前位置的item是否是第一列（仅当列表没有添加Header的时候，如果有添加Header请另行判断）
     *
     * @param parent   RecyclerView列表
     * @param position 当前item的位置
     */
    public static boolean isFirstColumn(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return false;

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            final int spanCount = manager.getSpanCount();

            return manager.getOrientation() == RecyclerView.VERTICAL ? position % spanCount == 0 : position < spanCount;
        } else if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.VERTICAL || position == 0;
        }

        return false;
    }

    /**
     * 判断当前位置的item是否是最后一列（仅当列表没有添加Footer的时候，如果有添加Footer请另行判断）
     *
     * @param parent   RecyclerView列表
     * @param position 当前item的位置
     */
    public static boolean isLastColumn(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return false;

        final int childCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            final int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL) {
                return (position + 1) % spanCount == 0;
            } else {

                int result = childCount % spanCount;
                return result == 0 ? position >= childCount - spanCount : position >= childCount - result;
            }
        } else if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.VERTICAL || position == childCount - 1;
        }

        return false;
    }

    /**
     * 获取当前item所在第几列（仅当列表没有添加Header的时候，如果有添加Header请另行判断）
     *
     * @param parent   RecyclerView
     * @param position 当前item的位置
     */
    public static int getCurrentColumnIndex(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return 0;

        if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
            return manager.getOrientation() == RecyclerView.VERTICAL ? 1 : position;
        } else if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL) {
                return position % spanCount + 1;
            } else {
                return position / spanCount + 1;
            }
        }

        return 0;
    }

    /**
     * 获取当前item所在第几行（仅当列表没有添加Header的时候，如果有添加Header请另行判断）
     *
     * @param parent   RecyclerView
     * @param position 当前item的位置
     */
    public static int getCurrentRowIndex(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return 0;

        if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.VERTICAL ? position : 1;
        } else if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL) {
                return position / spanCount + 1;
            } else {
                return position % spanCount + 1;
            }
        }

        return 0;
    }

}
