package com.finham.taobaocoupon.presenter.implement;

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
//逻辑层
public class HomePresenterImpl implements IHomePresenter {
    private IHomeCallback mCallback;

    @Override
    public void getCategories() {
        if (mCallback != null) mCallback.onLoading();
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
                    if (mCallback != null) {
                        //category = null; //用来测试
                        if (category == null || category.getData().size() == 0) {
                            mCallback.onEmpty();
                        } else
                            mCallback.onCategoriesLoaded(category);//从这边传到HomeFragment的onCategoriesLoaded()中了！
                    }
                } else {
                    //请求失败
//                    Log.d("123", "请求错误");
                    if (mCallback != null) mCallback.onError();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                //加载失败，有可能没网等原因
//                Log.d("123", "请求错误" + t);
                if (mCallback != null) mCallback.onError();
            }
        });
    }

    /**
     * 因为只有HomeFragment用到category，所以只使用一个callback就好了。
     * 如果有多个页面都要用到category，那么就要保证界面上的统一更新，就要用【集合】保存各个界面的callback，一通知的时候就for循环全部更新。
     * 例如之前的喜马拉雅，一个地方改变播放状态，那么其他地方也要跟着改。OK大概明白了，后面有时间再把喜马拉雅做一下也行~
     *
     * @param callback
     */
    @Override
    public void registerViewCallback(IHomeCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void unregisterCallback(IHomeCallback callback) {
        //取消注册的话置空就好了
        mCallback = null;
    }
}
