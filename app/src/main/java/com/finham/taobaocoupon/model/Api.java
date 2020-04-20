package com.finham.taobaocoupon.model;

import com.finham.taobaocoupon.model.domain.Category;
import com.finham.taobaocoupon.model.domain.HomePagerContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * User: Fin
 * Date: 2020/4/17
 * Time: 18:17
 */

/**
 * 对应Retrofit的Api类
 */
public interface Api {
    @GET("discovery/categories") //最前面不要加/，在baseUrl中加就好了
    Call<Category> getCategories();

    @GET
    Call<HomePagerContent> getHomePagerContent(@Url  String url);
    //因为字段为discovery/{materialId}/{page}，有{}，所以需要拼接一下
}
