package com.finham.taobaocoupon.model;

import com.finham.taobaocoupon.model.domain.Category;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * User: Fin
 * Date: 2020/4/17
 * Time: 18:17
 */
public interface Api {
    @GET("discovery/categories") //最前面不要加/，在baseUrl中加就好了
    Call<Category> getCategories();
}
