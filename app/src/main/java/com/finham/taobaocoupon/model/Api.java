package com.finham.taobaocoupon.model;

import com.finham.taobaocoupon.model.domain.Category;
import com.finham.taobaocoupon.model.domain.HomePagerContent;
import com.finham.taobaocoupon.model.domain.PreferentialContent;
import com.finham.taobaocoupon.model.domain.SelectedCategory;
import com.finham.taobaocoupon.model.domain.SelectedContent;
import com.finham.taobaocoupon.model.domain.Ticket;
import com.finham.taobaocoupon.model.domain.TicketParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @GET("discovery/categories")
        //最前面不要加/，在baseUrl中加就好了
    Call<Category> getCategories();

    @GET
    Call<HomePagerContent> getHomePagerContent(@Url String url);
    //因为字段为discovery/{materialId}/{page}，有{}，所以需要拼接一下，所以不在GET里写，而是以参数传入

    @POST("tpwd")
        //注意这边是POST，因为文档上就是写方法用POST！获取数据用POST是有点奇怪，但是没办法~
    Call<Ticket> getTicketContent(@Body TicketParams ticketParams);

    @GET("recommend/categories")
    Call<SelectedCategory> getSelectedCategory();

    @GET
    Call<SelectedContent> getSelectedContent(@Url String url);
    //加了注解的话就等于：@GET("recommend/categoryId?categoryId=xxxx")这样的形式
    //这部分推荐去看网络编程相关课程！

    /**
     * 获取特惠内容
     */
    @GET//("onSell/{page}") //可以这样写，也可以直接用@Url在参数中（形参）
    Call<PreferentialContent> getPreferentialContent(@Url String url);
}
