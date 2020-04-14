package com.finham.taobaocoupon.utils;

import android.util.Log;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 20:05
 */
public class LogUtils {
    public static final int verboseLevel = 5;
    public static final int debugLevel = 4;
    public static final int infoLevel = 3;
    public static final int warningLevel = 2;
    public static final int errorLevel = 1;
    public static final int nothingLevel = 0;
    public static int currentLevel = verboseLevel;

    public static void v(Class clazz, String log) {
        if (currentLevel >= verboseLevel) Log.v(clazz.getSimpleName(), log);
    }

    public static void d(Class clazz, String log) {
        if (currentLevel >= debugLevel) Log.d(clazz.getSimpleName(), log);
    }

    public static void i(Class clazz, String log) {
        if (currentLevel >= infoLevel) Log.i(clazz.getSimpleName(), log);
    }

    public static void w(Class clazz, String log) {
        if (currentLevel >= warningLevel) Log.w(clazz.getSimpleName(), log);
    }

    public static void e(Class clazz, String log) {
        if (currentLevel >= errorLevel) Log.e(clazz.getSimpleName(), log);
    }

}
