package com.finham.taobaocoupon.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.finham.taobaocoupon.base.BaseApplication;
import com.finham.taobaocoupon.model.domain.CacheWithDuration;
import com.google.gson.Gson;

/**
 * User: Fin
 * Date: 2020/5/10
 * Time: 21:22
 */
public class JsonCacheUtils {
    SharedPreferences mSharedPreferences;
    public static final String JSON_CACHE_SP_NAME = "json_cache_sp_name";
    private static JsonCacheUtils mInstance;
    private final Gson mGson;

    private JsonCacheUtils() {
        mSharedPreferences = BaseApplication.getContext().getSharedPreferences(JSON_CACHE_SP_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    public static JsonCacheUtils getInstance() {
        if (mInstance == null) {
            mInstance = new JsonCacheUtils();
        }
        return mInstance;
    }

    public void save(String key, Object value) {
        this.save(key, value, -1L);
    }

    public void save(String key, Object value, long duration) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        //为了把Json转为String，引入Gson
        String valueStr = mGson.toJson(value);
        if (duration != -1L) {
            duration += System.currentTimeMillis();
        }
        CacheWithDuration cache = new CacheWithDuration(duration, valueStr);
        String cacheStr = mGson.toJson(cache);
        editor.putString(key, cacheStr);
        editor.apply(); //官方还是推荐使用apply啊
        //那么duration如何放进去呢？再封装一次，创建新bean类。如上所示，这样就保存了一个带有时间的数据了
    }

    public void remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    public <T extends Class> T getValue(String key, Class<T> clazz) {
        String cache = mSharedPreferences.getString(key, "");
        CacheWithDuration cacheWithDuration = mGson.fromJson(cache, CacheWithDuration.class);
        long time = cacheWithDuration.getDuration();
        //判断是否过期
        if (time != -1L && time - System.currentTimeMillis() >= 0) {
            //过期了
            return null;
        } else {
            //没过期
            String cacheStr = cacheWithDuration.getCache();
            T t = mGson.fromJson(cacheStr, clazz);
            return t;
        }
    }

}
