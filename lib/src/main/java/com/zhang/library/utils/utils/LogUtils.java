package com.zhang.library.utils.utils;

import android.text.TextUtils;
import android.util.Log;

import com.zhang.library.utils.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

/**
 * 日志打印工具
 *
 * @author ZhangXiaoMing 2019-12-26 10:48 星期四
 */
public final class LogUtils {

    private static boolean IS_DEBUG;

    private LogUtils() {
        IS_DEBUG = BuildConfig.DEBUG;
    }

    public static void setIsDebug(boolean isDebug) {
        IS_DEBUG = isDebug;
    }

    public static String createLog(StackTraceElement[] traceElements, String msg) {
        String methodName = getMethodName(traceElements);
        int lineNumber = getLineNumber(traceElements);
        return new StringBuilder()
                .append("[")
                .append(methodName)
                .append(":")
                .append(lineNumber)
                .append("] ")
                .append(msg)
                .toString();
    }

    //<editor-fold desc="verbose">
    public static void verbose(String msg) {
        verbose(null, msg);
    }

    public static void verbose(String tag, String msg) {
        if (!IS_DEBUG)
            return;
        StackTraceElement[] traceElements = new Throwable().getStackTrace();
        if (TextUtils.isEmpty(tag))
            tag = getClassName(traceElements);
        String log = createLog(traceElements, msg);
        Log.v(tag, log);
    }

    public static void verbose(String format, Object... args) {
        verbose(null, format, args);
    }

    public static void verbose(String tag, String format, Object... args) {
        String msg;
        if (TextUtils.isEmpty(format))
            msg = " ";
        else
            msg = args == null || args.length <= 0 ? format : String.format(Locale.US, format, args);
        verbose(tag, msg);
    }
    //</editor-fold>

    //<editor-fold desc="debug">
    public static void debug(String msg) {
        debug(null, msg);
    }

    public static void debug(String tag, String msg) {
        if (!IS_DEBUG)
            return;
        StackTraceElement[] traceElements = new Throwable().getStackTrace();
        if (TextUtils.isEmpty(tag))
            tag = getClassName(traceElements);
        String log = createLog(traceElements, msg);
        Log.d(tag, log);
    }

    public static void debug(String format, Object... args) {
        debug(null, format, args);
    }

    public static void debug(String tag, String format, Object... args) {
        String msg;
        if (TextUtils.isEmpty(format))
            msg = " ";
        else
            msg = args == null || args.length <= 0 ? format : String.format(Locale.US, format, args);
        debug(tag, msg);
    }
    //</editor-fold>

    //<editor-fold desc="info">
    public static void info(String msg) {
        info(null, msg);
    }

    public static void info(String tag, String msg) {
        if (!IS_DEBUG)
            return;
        StackTraceElement[] traceElements = new Throwable().getStackTrace();
        if (TextUtils.isEmpty(tag))
            tag = getClassName(traceElements);
        String log = createLog(traceElements, msg);
        Log.i(tag, log);
    }

    public static void info(String format, Object... args) {
        info(null, format, args);
    }

    public static void info(String tag, String format, Object... args) {
        String msg;
        if (TextUtils.isEmpty(format))
            msg = " ";
        else
            msg = args == null || args.length <= 0 ? format : String.format(Locale.US, format, args);
        info(tag, msg);
    }
    //</editor-fold>

    //<editor-fold desc="warn">
    public static void warn(String msg) {

    }

    public static void warn(String tag, String msg) {
        if (!IS_DEBUG)
            return;
        StackTraceElement[] traceElements = new Throwable().getStackTrace();
        if (TextUtils.isEmpty(tag))
            tag = getClassName(traceElements);
        String log = createLog(traceElements, msg);
        Log.w(tag, log);
    }
    //</editor-fold>

    //<editor-fold desc="error">
    public static void error(String msg) {
        error(null, msg);
    }

    public static void error(String tag, String msg) {
        if (!IS_DEBUG)
            return;
        StackTraceElement[] traceElements = new Throwable().getStackTrace();
        if (TextUtils.isEmpty(tag))
            tag = getClassName(traceElements);
        String log = createLog(traceElements, msg);
        Log.e(tag, log);
    }

    public static void error(String format, Object... args) {
        error(null, format, args);
    }

    public static void error(String tag, String format, Object... args) {
        String msg;
        if (TextUtils.isEmpty(format))
            msg = " ";
        else
            msg = args == null || args.length <= 0 ? format : String.format(Locale.US, format, args);
        error(tag, msg);
    }
    //</editor-fold>

    /**
     * Formats the caller's provided message and prepends useful info like calling thread ID and
     * method name.
     */
    private static String buildMessage(String format, boolean showSource, Object... args) {
        if (TextUtils.isEmpty(format)) {
            return " ";
//            return "format is null ";
        }
        String msg = args == null || args.length <= 0 ? format : String.format(Locale.US, format, args);
        String logSource = " ";
        if (showSource) {
            try {
                logSource = getLogSource();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), logSource, msg);
    }

    /**
     * getStackTraceAsString:(get ex to string)
     *
     * @param ex The Throwable been thrown
     *
     * @return String
     * @since CodingExample　Ver 1.1
     */
    public static String getStackTraceAsString(Throwable ex) {
        if (ex == null) {
            return "Sorry , throwable is Null!!! Unknown throwable info!!!";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        StringBuilder exceptionContent = new StringBuilder();
        try {
            exceptionContent.append(getLogSource()).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        exceptionContent.append(stringWriter.getBuffer());
        return exceptionContent.toString();
    }

    private static String getClassName(StackTraceElement[] traceElements) {
        String fileName = traceElements[2].getFileName();
        int indexOf = fileName.indexOf(".java");
        if (indexOf == -1) {
            indexOf = fileName.indexOf(".Java");
        }
        if (indexOf == -1) {
            return fileName;
        }
        fileName = fileName.substring(0, indexOf);
        return fileName;
    }

    private static String getMethodName(StackTraceElement[] traceElements) {
        return traceElements[1].getMethodName();
    }

    private static int getLineNumber(StackTraceElement[] traceElements) {
        return traceElements[1].getLineNumber();
    }

    /** 获取日志来源 */
    private static String getLogSource() {
        StackTraceElement[] traceElements = new Throwable().fillInStackTrace().getStackTrace();

        String symbol1 = "->", symbol2 = ".", symbol3 = "(", symbol4 = ")", symbol5 = ":";
        String qposStr = "qpos";
        StringBuilder builder = new StringBuilder();
        //保存日志调用记录
        for (int i = traceElements.length - 1; i >= 2; i--) {
            StackTraceElement trace = traceElements[i];
            String callingClass = trace.getClassName();
            if (!callingClass.contains(qposStr)) {
                continue;
            }
            callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
            callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
            if (!TextUtils.isEmpty(builder)) {
                builder.append(symbol1);
            }
            builder.append(callingClass).append(symbol2).append(trace.getMethodName())
                    .append(symbol3).append(trace.getFileName()).append(symbol5).append(trace.getLineNumber()).append(symbol4);
        }
        return builder.toString();
    }

}
