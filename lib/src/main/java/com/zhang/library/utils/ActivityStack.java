package com.zhang.library.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;

/**
 * Activity队列
 *
 * @author ZhangXiaoMing 2020-01-04 17:22 星期六
 */
public class ActivityStack {

    private static final String TAG = "ActivityStack";

    private static volatile ActivityStack instance;

    private Application mApplication;
    private final List<Activity> mActivityHolder = new CopyOnWriteArrayList<>();

    private boolean isDebug;

    private ActivityStack() {
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

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public synchronized void init(Application application) {
        if (mApplication == null) {
            mApplication = application;
            mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }
    }

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
            LogUtils.info(TAG, "%s>>>onResume()", activity.getClass().getSimpleName());
            final int index = mActivityHolder.indexOf(activity);
            if (index < 0)
                return;

            final int size = mActivityHolder.size();
            if (size <= 1)
                return;

            if (index != (size - 1)) {
                if (isDebug)
                    Log.e(TAG, "start order activity " + activity + " old index " + index);

                removeActivity(activity);
                addActivity(activity);

                if (isDebug)
                    Log.e(TAG, "end order activity " + activity + " new index " + mActivityHolder.indexOf(activity));
            }
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            LogUtils.info(TAG, "%s>>>onPause()", activity.getClass().getSimpleName());
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
        if (arrActivity != null) {
            return Arrays.toString(arrActivity);
        } else {
            return "";
        }
    }

    /**
     * 添加对象
     *
     * @param activity
     */
    private void addActivity(Activity activity) {
        if (mActivityHolder.contains(activity))
            return;

        mActivityHolder.add(activity);

        if (isDebug) {
            Log.i(TAG, "+++++ " + activity + " " + mActivityHolder.size()
                    + "\r\n" + getCurrentStack());
        }
    }

    /**
     * 移除对象
     *
     * @param activity
     */
    private void removeActivity(Activity activity) {
        if (mActivityHolder.remove(activity)) {
            if (isDebug) {
                Log.e(TAG, "----- " + activity + " " + mActivityHolder.size()
                        + "\r\n" + getCurrentStack());
            }
        }
    }

    /**
     * 返回栈中保存的对象个数
     *
     * @return
     */
    public int size() {
        return mActivityHolder.size();
    }

    /**
     * 返回栈中指定位置的对象
     *
     * @param index
     *
     * @return
     */
    public Activity getActivity(int index) {
        try {
            return mActivityHolder.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回栈中最后一个对象
     *
     * @return
     */
    public Activity getLastActivity() {
        return getActivity(mActivityHolder.size() - 1);
    }

    /**
     * 是否包含指定对象
     *
     * @param activity
     *
     * @return
     */
    public boolean containsActivity(Activity activity) {
        return mActivityHolder.contains(activity);
    }

    /**
     * 返回栈中指定类型的所有对象
     *
     * @param clazz
     *
     * @return
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
     * @param clazz
     *
     * @return
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
     * @param clazz
     *
     * @return
     */
    public boolean containsActivity(Class<? extends Activity> clazz) {
        return getFirstActivity(clazz) != null;
    }

    /**
     * 结束栈中指定类型的对象
     *
     * @param clazz
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
     * @param activity
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
     * @param clazz
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
     * @param activity
     */
    public void finishSameClassActivityExcept(Activity activity) {
        for (Activity item : mActivityHolder) {
            if (item.getClass() == activity.getClass()) {
                if (item != activity)
                    item.finish();
            }
        }
    }

    /**
     * 结束所有对象
     */
    public void finishAllActivity() {
        for (Activity item : mActivityHolder) {
            item.finish();
        }
    }
}
