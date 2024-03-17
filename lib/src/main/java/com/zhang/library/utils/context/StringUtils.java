package com.zhang.library.utils.context;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;

import com.zhang.library.utils.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字工具类
 *
 * @author ZhangXiaoMing 2020-06-19 16:45 星期五
 */
public class StringUtils extends ContextUtils {

    /**
     * TextView中英文混合时会提前换行，以这个方法切换半角、全角，规避这个现象
     *
     * @param input 要优化的文字
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 获得关键字亮色显示的SpannableString
     *
     * @param text   要显示的文字
     * @param target 关键字
     */
    public static SpannableString getHighLightSpanString(String text, String target) {
        return getHighLightSpanString(text, target, getColor(R.color.colorPrimary));
    }

    /**
     * 获得关键字亮色显示的SpannableString
     *
     * @param text   要显示的文字
     * @param target 关键字
     * @param color  高亮颜色
     */
    public static SpannableString getHighLightSpanString(String text, String target, int color) {
        SpannableString spannableString = new SpannableString(text);

        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            spannableString.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    /**
     * 转换字体颜色，将[font]和[/font]格式的文案，替换成html中的font标签
     *
     * @param content 内容
     * @param color   十六进制颜色码格式，例如：#FFFFFF
     */
    public static CharSequence transformFontColor(@NonNull String content, @NonNull String color) {
        String labelStart = "<font color='" + color + "'>";
        String labelEnd = "</font>";

        content = content.replace("[font]", labelStart);
        content = content.replace("[/font]", labelEnd);

        return Html.fromHtml(content);
    }

    /**
     * 转换字体颜色，将[font]和[/font]格式的文案，替换成html中的font标签
     *
     * @param content 内容
     * @param colors  十六进制颜色码格式，例如：#FFFFFF
     */
    public static CharSequence transformFontColor(@NonNull String content, @NonNull String... colors) {
        int index = 0;
        while (content.contains("[font]") && content.contains("[/font]")) {
            String labelStart = "<font color='" + colors[index] + "'>";
            String labelEnd = "</font>";

            content = content.replaceFirst("\\[font]", labelStart);
            content = content.replaceFirst("\\[/font]", labelEnd);

            index++;
            if (index >= colors.length)
                break;
        }

        return Html.fromHtml(content);
    }

}
