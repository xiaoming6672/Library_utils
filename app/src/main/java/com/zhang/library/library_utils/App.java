package com.zhang.library.library_utils;

import android.app.Application;

import com.zhang.library.utils.ActivityStack;
import com.zhang.library.utils.LogUtils;
import com.zhang.library.utils.context.ContextUtils;

/**
 * @author ZhangXiaoMing 2023-06-14 15:38 周三
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ContextUtils.set(this);
        LogUtils.setDebug(BuildConfig.DEBUG);

        ActivityStack.getInstance().init(this);
    }
}
