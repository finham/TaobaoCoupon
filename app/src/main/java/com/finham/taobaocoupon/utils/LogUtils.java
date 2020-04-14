package com.finham.taobaocoupon.utils;

import android.util.Log;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 20:05
 */
public class LogUtils {
    private static final int VERBOSE_LEVEL = 5;
    private static final int DEBUG_LEVEL = 4;
    private static final int INFO_LEVEL = 3;
    private static final int WARNING_LEVEL = 2;
    private static final int ERROR_LEVEL = 1;
    private static int currentLevel = VERBOSE_LEVEL;

    public static void v(Class clazz, String log) {
        if (currentLevel >= VERBOSE_LEVEL) Log.v(clazz.getSimpleName(), log);
    }

    public static void d(Class clazz, String log) {
        if (currentLevel >= DEBUG_LEVEL) Log.d(clazz.getSimpleName(), log);
    }

    public static void i(Class clazz, String log) {
        if (currentLevel >= INFO_LEVEL) Log.i(clazz.getSimpleName(), log);
    }

    public static void w(Class clazz, String log) {
        if (currentLevel >= WARNING_LEVEL) Log.w(clazz.getSimpleName(), log);
    }

    public static void e(Class clazz, String log) {
        if (currentLevel >= ERROR_LEVEL) Log.e(clazz.getSimpleName(), log);
    }

}
