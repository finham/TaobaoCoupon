package com.finham.taobaocoupon.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User: Fin
 * Date: 2020/4/17
 * Time: 18:22
 */
public class RetrofitManager {
    private Retrofit mRetrofit;

    //系统帮咱们创建的单例是饿汉模式，虽然可能造成内存上的损耗（在这里不会，因为网络请求是一定要使用的），并且是线程安全的。
    private static final RetrofitManager ourInstance = new RetrofitManager();

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    private RetrofitManager() {
        //创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}
