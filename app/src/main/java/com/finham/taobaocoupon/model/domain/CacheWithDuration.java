package com.finham.taobaocoupon.model.domain;

/**
 * User: Fin
 * Date: 2020/5/10
 * Time: 22:26
 */
public class CacheWithDuration {
    private long duration;
    private String cache;

    public CacheWithDuration(long duration, String cache) {
        this.duration = duration;
        this.cache = cache;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }
}
