package com.zhang.library.utils.utils.context;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

/**
 * 设备工具类
 *
 * @author ZhangXiaoMing 2020-08-11 10:11 星期二
 */
public class DeviceUtils extends ContextUtils {

    /** 获取设备屏幕宽度 */
    public static int getWidth() {
        return ResUtils.getScreenWidth();
    }

    /** 获取设备屏幕高度 */
    public static int getHeight() {
        return ResUtils.getScreenHeight();
    }

    /** 获取系统语言 */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /** 获取系统版本 */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /** 获取设备型号 */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /** 获取设备名 */
    public static String getSystemDevice() {
        return Build.DEVICE;
    }

    /** 获取设备品牌 */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /** 获取设备主板名 */
    public static String getDeviceBoard() {
        return Build.BOARD;
    }

    /** 获取设备生产厂商 */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /** 获取设备的IMSI */
    public static String getIMSI() {
        try {
            TelephonyManager manager = (TelephonyManager) get().getSystemService(Context.TELEPHONY_SERVICE);

            String imei = manager.getSubscriberId();
            if (TextUtils.isEmpty(imei)) {
                imei = getAndroidId();
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /** 获取设备的IMEI */
    public static String getIMEI() {
        try {
            TelephonyManager manager = (TelephonyManager) get().getSystemService(Context.TELEPHONY_SERVICE);

            String imei = manager.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                imei = getAndroidId();
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /** 获取设备的android_id */
    public static String getAndroidId() {
        return Settings.Secure.getString(get().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /** 获取App的versionName */
    public static String getVersionName() {
        try {
            return get().getPackageManager().getPackageInfo(get().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /** 获取App的versionCode */
    public static int getVersionCode() {
        try {
            return get().getPackageManager().getPackageInfo(get().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
