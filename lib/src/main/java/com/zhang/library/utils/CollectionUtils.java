package com.zhang.library.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数组集合工具类
 *
 * @author ZhangXiaoMing 2020-01-03 17:21 星期五
 */
public class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * 判断列表是否为空
     *
     * @param list 列表
     *
     * @return <b>true:</b>列表为空
     */
    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 获取列表的长度
     *
     * @param list 列表
     */
    public static int getSize(Collection<?> list) {
        return isEmpty(list) ? 0 : list.size();
    }

    /**
     * 判断索引位置在列表中是否合法
     *
     * @param list  列表
     * @param index 索引位置
     */
    public static boolean isIndexLegal(Collection<?> list, int index) {
        if (isEmpty(list)) {
            return false;
        } else {
            int size = list.size();
            return isIndexLegal(size, index);
        }
    }

    /**
     * 判断索引位置是否合法
     *
     * @param size  列表长度
     * @param index 索引位置
     */
    public static boolean isIndexLegal(int size, int index) {
        return index >= 0 && index < size;
    }

    /**
     * 判断列表是否包含数据
     *
     * @param list 列表
     * @param data 数据
     * @param <T>  列表数据泛型
     */
    public static <T> boolean contains(Collection<T> list, T data) {
        if (isEmpty(list))
            return false;

        return list.contains(data);
    }

    /**
     * 获取列表指定索引位置的数据
     *
     * @param list  列表
     * @param index 索引位置
     * @param <T>   列表数据泛型
     */
    public static <T> T get(List<T> list, int index) {
        return !isIndexLegal(list, index) ? null : list.get(index);
    }

    /**
     * 获取列表指定索引位置（倒序）的数据
     *
     * @param list  列表
     * @param index 索引位置，倒序
     * @param <T>   列表数据泛型
     */
    public static <T> T getLast(List<T> list, int index) {
        if (!isIndexLegal(list, index)) {
            return null;
        } else {
            index = list.size() - 1 - index;
            return list.get(index);
        }
    }

    /**
     * 分割列表
     *
     * @param list         列表
     * @param countPerList 每个列表的数量
     * @param <T>          列表数据泛型
     */
    public static <T> List<List<T>> splitList(List<T> list, int countPerList) {
        if (!isEmpty(list) && countPerList > 0) {
            List<List<T>> listGroup = new ArrayList<>();
            List<T> listPage = new ArrayList<>();

            for (int i = 0; i < list.size(); ++i) {
                listPage.add(list.get(i));
                if (i != 0 && (i + 1) % countPerList == 0) {
                    listGroup.add(listPage);
                    listPage = new ArrayList<>();
                }
            }

            if (listPage.size() > 0) {
                listGroup.add(listPage);
            }

            return listGroup;
        } else {
            return null;
        }
    }

    /**
     * 截取列表
     *
     * @param list  列表
     * @param start 起始位置
     * @param end   结束位置
     */
    public static <T> List<T> subList(List<T> list, int start, int end) {
        return !isEmpty(list) && isIndexLegal(list, start) && isIndexLegal(list, end) ? list.subList(start, end) : null;
    }

    /**
     * 复制列表
     *
     * @param list  列表
     * @param start 起始位置
     * @param end   结束位置
     */
    public static <T> List<T> copyList(List<T> list, int start, int end) {
        if (!isEmpty(list) && isIndexLegal(list, start) && isIndexLegal(list, end) && start <= end) {
            List<T> listResult = new ArrayList<>();

            for (int i = start; i < end; ++i) {
                listResult.add(list.get(i));
            }

            return listResult;
        } else {
            return null;
        }
    }
}
