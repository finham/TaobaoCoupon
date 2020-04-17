package com.finham.taobaocoupon.presenter.implement;

import android.util.Log;

import com.finham.taobaocoupon.model.Api;
import com.finham.taobaocoupon.model.domain.Category;
import com.finham.taobaocoupon.presenter.IHomePresenter;
import com.finham.taobaocoupon.utils.RetrofitManager;
import com.finham.taobaocoupon.view.IHomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * User: Fin
 * Date: 2020/4/17
 * Time: 16:44
 */
public class HomePresenterImpl implements IHomePresenter {

    @Override
    public void getCategories() {
        //通过Retrofit单例获取到一个retrofit对象
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        //Create an implementation of the API endpoints defined by the interface.根据接口创建是实现类
        Api api = retrofit.create(Api.class);
        //通过baseUrl和GET里面的拼接获取
        /**
         * An invocation of a Retrofit method that sends a request to a webserver and returns a response.
         * Each call yields its own HTTP request and response pair.
         * 对Retrofit方法的调用，该方法将请求发送到Web服务器并返回响应。每个调用都会产生自己的HTTP请求和响应对。
         */
        Call<Category> task = api.getCategories();  //yield产出的意思
        //异步发送请求并通知回调
        task.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                //数据结果
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    //请求成功
                    Category category = response.body();
//                    LogUtils.d(HomePresenterImpl.class, category.toString());
                    Log.d("123",category.toString());
                } else {
                    //请求失败
//                    LogUtils.i(HomePresenterImpl.class, "请求错误");
                    Log.d("123","请求错误");
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                //加载失败，有可能没网等原因
//                LogUtils.e(HomePresenterImpl.class, "请求错误" + t);
                Log.d("123","请求错误"+t);
            }
        });
    }

    @Override
    public void registerCallback(IHomeCallback callback) {

    }

    @Override
    public void unregisterCallback(IHomeCallback callback) {

    }
}
