package com.bolue.scan.utils;

import com.bolue.scan.application.App;

/**
 * Created by cty on 2016/12/12.
 */

public class DimenUtil {

    public static float dp2px(float dp) {
        final float scale = App.getAppContext().getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(float sp) {
        final float scale = App.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int getScreenWidth() {
        return App.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return App.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }
}
