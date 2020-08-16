package com.zhang.library.utils.utils.context;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

/**
 * 剪切板工具
 *
 * @author ZhangXiaoMing 2020-08-16 21:33 星期日
 */
public class ClipboardUtils extends ContextUtils {

    /**
     * 设置剪切板复制的内容
     *
     * @param content 文本 - 在剪辑的实际文本
     */
    public static void setCopyContent(CharSequence content) {
        setCopyContent(null, content);
    }

    /**
     * 设置剪切板复制的内容
     *
     * @param label   标签 - 用户可见的标签剪辑数据。
     * @param content 文本 - 在剪辑的实际文本
     */
    public static void setCopyContent(CharSequence label, CharSequence content) {
        final ClipboardManager manager = (ClipboardManager) get().getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(label, content));
    }

    /** 获取剪切板内容 */
    public static CharSequence getCopyContent() {
        final ClipboardManager manager = (ClipboardManager) get().getSystemService(Context.CLIPBOARD_SERVICE);
        if (!manager.hasPrimaryClip())
            return null;

        final ClipData clipData = manager.getPrimaryClip();
        if (clipData == null)
            return null;

        final int count = clipData.getItemCount();
        if (count <= 0)
            return null;

        final ClipData.Item item = clipData.getItemAt(0);
        if (item == null)
            return null;

        return item.getText();
    }

    /**
     * 获取剪切板内容，如果剪切板内容标签和指定的不一致，则返回空
     *
     * @param label 指定的标签
     */
    public static CharSequence getCopyContent(CharSequence label) {
        if (TextUtils.isEmpty(label)) {
            return getCopyContent();
        }

        final ClipboardManager manager = (ClipboardManager) get().getSystemService(Context.CLIPBOARD_SERVICE);
        if (!manager.hasPrimaryClip())
            return null;

        final ClipData clipData = manager.getPrimaryClip();
        if (clipData == null)
            return null;

        final int count = clipData.getItemCount();
        if (count <= 0)
            return null;

        ClipDescription description = clipData.getDescription();
        if (description == null) {
            return null;
        }

        if (!label.equals(description.getLabel())) {
            return null;
        }

        final ClipData.Item item = clipData.getItemAt(0);
        if (item == null)
            return null;

        return item.getText();
    }

    /** 获得剪切板纯文字内容 */
    public static String getCopyContentString() {
        ClipboardManager manager = (ClipboardManager) get().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager == null)
            return null;
        if (!manager.hasPrimaryClip())
            return null;
        ClipData clipData = manager.getPrimaryClip();
        if (clipData == null)
            return null;
        if (clipData.getItemCount() <= 0)
            return null;

        ClipData.Item item = clipData.getItemAt(0);
        return item.coerceToText(get()).toString();
    }

    /** 获取剪切板数据对象 */
    public static ClipData getClipData() {
        ClipboardManager manager = (ClipboardManager) get().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager == null)
            return null;
        if (!manager.hasPrimaryClip())
            return null;
        ClipData clipData = manager.getPrimaryClip();
        if (clipData == null)
            return null;
        if (clipData.getItemCount() <= 0)
            return null;

        return clipData;
    }

    /** 获取当前剪切板数据的标签 */
    public static CharSequence getCopyLabel() {
        ClipboardManager manager = (ClipboardManager) get().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager == null)
            return null;
        if (!manager.hasPrimaryClip())
            return null;
        ClipData clipData = manager.getPrimaryClip();
        if (clipData == null)
            return null;
        if (clipData.getItemCount() <= 0)
            return null;

        ClipDescription description = clipData.getDescription();
        if (description == null) {
            return null;
        }

        return description.getLabel();
    }

}
