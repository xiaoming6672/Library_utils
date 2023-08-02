package com.zhang.library.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Activity队列
 *
 * @author ZhangXiaoMing 2020-01-04 17:22 星期六
 */
public class ActivityStack {

    private static final String TAG = "ActivityStack";

    private static volatile ActivityStack instance;

    private Application mApplication;
    private final List<Activity> mActivityHolder;
    /** 活跃数量 */
    private int mActiveCount;

    /** App切换到后台的时间戳 */
    private long mApplicationHiddenTimestamp;
    /** App是否切换到后台 */
    private boolean isApplicationHidden;
    private final List<ApplicationStateChangedCallback> mCallbackList;

    private ActivityStack() {
        mActivityHolder = new CopyOnWriteArrayList<>();
        mCallbackList = new ArrayList<>();
        mActiveCount = 0;
    }

    public static ActivityStack getInstance() {
        if (instance == null) {
            synchronized (ActivityStack.class) {
                if (instance == null)
                    instance = new ActivityStack();
            }
        }
        return instance;
    }

    public synchronized void init(Application application) {
        if (mApplication == null) {
            mApplication = application;
            mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }
    }


    /**
     * 注册Application状态变更回调
     *
     * @param callback 回调
     */
    public void registerApplicationStateChangedCallback(ApplicationStateChangedCallback callback) {
        if (callback == null)
            return;

        if (!mCallbackList.contains(callback))
            mCallbackList.add(callback);
    }

    /**
     * 注销Application状态变更回调
     *
     * @param callback 回调
     */
    public void unregisterApplicationStateChangedCallback(ApplicationStateChangedCallback callback) {
        mCallbackList.remove(callback);
    }


    /** Application的Activity生命周期回调 */
    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
            LogUtils.info(TAG, "%s>>>onCreate()", activity.getClass().getSimpleName());
            addActivity(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            LogUtils.info(TAG, "%s>>>onStart()", activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            mActiveCount++;

            if (isApplicationHidden) {
                isApplicationHidden = false;

                long nowTime = System.currentTimeMillis();
                long hiddenDuration = nowTime - mApplicationHiddenTimestamp;

                for (ApplicationStateChangedCallback callback : mCallbackList) {
                    callback.onApplicationShown(hiddenDuration);
                }
            }

            LogUtils.info(TAG, "%s>>>onResume()", activity.getClass().getSimpleName());
            final int index = mActivityHolder.indexOf(activity);
            if (index < 0)
                return;

            final int size = mActivityHolder.size();
            if (size <= 1)
                return;

            if (index != (size - 1)) {
                LogUtils.error(TAG, "start order activity " + activity + " old index " + index);

                removeActivity(activity);
                addActivity(activity);

                LogUtils.error(TAG, "end order activity " + activity + " new index " + mActivityHolder.indexOf(activity));
            }
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            LogUtils.info(TAG, "%s>>>onPause()", activity.getClass().getSimpleName());
            mActiveCount--;

            if (mActiveCount <= 0) {
                isApplicationHidden = true;
                mApplicationHiddenTimestamp = System.currentTimeMillis();

                for (ApplicationStateChangedCallback callback : mCallbackList) {
                    callback.onApplicationHidden();
                }
            }
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
            LogUtils.info(TAG, "%s>>>onStop()", activity.getClass().getSimpleName());
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
            LogUtils.info(TAG, "%s>>>onSaveInstanceState()", activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            LogUtils.info(TAG, "%s>>>onDestroy()", activity.getClass().getSimpleName());
            removeActivity(activity);
        }
    };

    private String getCurrentStack() {
        Object[] arrActivity = mActivityHolder.toArray();
        return Arrays.toString(arrActivity);
    }

    /**
     * 添加对象
     *
     * @param activity activity对象
     */
    private void addActivity(Activity activity) {
        if (mActivityHolder.contains(activity))
            return;

        mActivityHolder.add(activity);

        LogUtils.info(TAG, "+++++ " + activity + " " + mActivityHolder.size()
                + "\r\n" + getCurrentStack());
    }

    /**
     * 移除对象
     *
     * @param activity activity对象
     */
    private void removeActivity(Activity activity) {
        if (mActivityHolder.remove(activity)) {
            LogUtils.error(TAG, "----- " + activity + " " + mActivityHolder.size()
                    + "\r\n" + getCurrentStack());
        }
    }

    /** 获取活跃的页面的数量 */
    public int getActiveCount() {
        return mActiveCount;
    }

    /** 返回栈中保存的对象个数 */
    public int size() {
        return mActivityHolder.size();
    }

    /**
     * 返回栈中指定位置的对象
     *
     * @param index 指定位置
     */
    public Activity getActivity(int index) {
        try {
            return mActivityHolder.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /** 返回栈中最后一个对象 */
    public Activity getLastActivity() {
        return getActivity(mActivityHolder.size() - 1);
    }

    /**
     * 是否包含指定对象
     *
     * @param activity activity对象
     */
    public boolean containsActivity(Activity activity) {
        return mActivityHolder.contains(activity);
    }

    /**
     * 返回栈中指定类型的所有对象
     *
     * @param clazz Activity的类对象
     */
    public List<Activity> getActivity(Class<? extends Activity> clazz) {
        final List<Activity> list = new ArrayList<>(1);
        for (Activity item : mActivityHolder) {
            if (item.getClass() == clazz)
                list.add(item);
        }
        return list;
    }

    /**
     * 返回栈中指定类型的第一个对象
     *
     * @param clazz Activity的类对象
     */
    public Activity getFirstActivity(Class<? extends Activity> clazz) {
        for (Activity item : mActivityHolder) {
            if (item.getClass() == clazz)
                return item;
        }
        return null;
    }

    /**
     * 栈中是否包含指定类型的对象
     *
     * @param clazz Activity的类对象
     */
    public boolean containsActivity(Class<? extends Activity> clazz) {
        return getFirstActivity(clazz) != null;
    }

    /**
     * 结束栈中指定类型的对象
     *
     * @param clazz Activity的类对象
     */
    public void finishActivity(Class<? extends Activity> clazz) {
        final List<Activity> list = getActivity(clazz);
        for (Activity item : list) {
            item.finish();
        }
    }

    /**
     * 结束栈中除了activity外的所有对象
     *
     * @param activity activity对象
     */
    public void finishActivityExcept(Activity activity) {
        for (Activity item : mActivityHolder) {
            if (item != activity)
                item.finish();
        }
    }

    /**
     * 结束栈中除了指定类型外的所有对象
     *
     * @param clazz Activity的类对象
     */
    public void finishActivityExcept(Class<? extends Activity> clazz) {
        for (Activity item : mActivityHolder) {
            if (item.getClass() != clazz)
                item.finish();
        }
    }

    /**
     * 结束栈中除了activity外的所有activity类型的对象
     *
     * @param activity activity对象
     */
    public void finishSameClassActivityExcept(Activity activity) {
        for (Activity item : mActivityHolder) {
            if (item.getClass() == activity.getClass()) {
                if (item != activity)
                    item.finish();
            }
        }
    }

    /** 结束所有对象 */
    public void finishAllActivity() {
        for (Activity item : mActivityHolder) {
            item.finish();
        }
    }


    public interface ApplicationStateChangedCallback {

        /** App被切换到后台 */
        default void onApplicationHidden() {
        }

        /**
         * App回到前台
         *
         * @param hiddenDuration 在后台的持续时间
         */
        default void onApplicationShown(long hiddenDuration) {
        }
    }

}
