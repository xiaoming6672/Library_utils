package com.zhang.library.utils.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * View的方向
 *
 * @author ZhangXiaoMing 2022-11-24 22:47 周四
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        ViewDirection.LEFT,
        ViewDirection.TOP,
        ViewDirection.RIGHT,
        ViewDirection.BOTTOM,
})
public @interface ViewDirection {

    int LEFT = 1;
    int TOP = 2;
    int RIGHT = 3;
    int BOTTOM = 4;
}
