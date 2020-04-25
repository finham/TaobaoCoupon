package com.finham.taobaocoupon.base;

import android.app.Application;
import android.content.Context;

/**
 * User: Fin
 * Date: 2020/4/25
 * Time: 14:36
 */
public class BaseApplication extends Application {

    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getBaseContext(); //注意这里使用的是 getBaseContext() 方法
    }

    public static Context getContext(){
        return sContext;
    }
}
