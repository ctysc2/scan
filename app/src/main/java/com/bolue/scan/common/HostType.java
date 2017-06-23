package com.bolue.scan.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cty on 2017/6/4.
 */

public class HostType {

    public static final int TYPE_COUNT = 2;
    public static final int LOGIN = 1;
    public static final int INDEX = 2;
    public static final int CAROUSEL = 3;
    public static final int RESERVE = 4;
    public static final int LABEL = 5;
    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({LOGIN,INDEX,CAROUSEL,RESERVE,LABEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }
}
