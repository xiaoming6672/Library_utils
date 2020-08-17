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

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static int getSize(Collection<?> list) {
        return isEmpty(list) ? 0 : list.size();
    }

    public static boolean isIndexLegal(Collection<?> list, int index) {
        if (isEmpty(list)) {
            return false;
        } else {
            int size = list.size();
            return isIndexLegal(size, index);
        }
    }

    public static boolean isIndexLegal(int size, int index) {
        return index >= 0 && index < size;
    }

    public static <T> T get(List<T> list, int index) {
        return !isIndexLegal(list, index) ? null : list.get(index);
    }

    public static <T> T getLast(List<T> list, int index) {
        if (!isIndexLegal(list, index)) {
            return null;
        } else {
            index = list.size() - 1 - index;
            return list.get(index);
        }
    }

    public static <T> List<List<T>> splitList(List<T> list, int countPerList) {
        if (!isEmpty(list) && countPerList > 0) {
            List<List<T>> listGroup = new ArrayList();
            List<T> listPage = new ArrayList();

            for (int i = 0; i < list.size(); ++i) {
                listPage.add(list.get(i));
                if (i != 0 && (i + 1) % countPerList == 0) {
                    listGroup.add(listPage);
                    listPage = new ArrayList();
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

    public static <T> List<T> subList(List<T> list, int start, int end) {
        return !isEmpty(list) && isIndexLegal(list, start) && isIndexLegal(list, end) ? list.subList(start, end) : null;
    }

    public static <T> List<T> copyList(List<T> list, int start, int end) {
        if (!isEmpty(list) && isIndexLegal(list, start) && isIndexLegal(list, end) && start <= end) {
            List<T> listResult = new ArrayList();

            for (int i = start; i < end; ++i) {
                listResult.add(list.get(i));
            }

            return listResult;
        } else {
            return null;
        }
    }
}
