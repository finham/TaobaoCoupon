package com.finham.taobaocoupon.presenter.implement;

import android.util.Log;

import com.finham.taobaocoupon.model.Api;
import com.finham.taobaocoupon.model.domain.SelectedCategory;
import com.finham.taobaocoupon.model.domain.SelectedContent;
import com.finham.taobaocoupon.presenter.ISelectedPagerPresenter;
import com.finham.taobaocoupon.utils.RetrofitManager;
import com.finham.taobaocoupon.utils.UrlUtils;
import com.finham.taobaocoupon.view.ISelectedPagerCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * User: Fin
 * Date: 2020/5/4
 * Time: 11:22
 */
public class SelectedPagerPresenterImpl implements ISelectedPagerPresenter {
    private ISelectedPagerCallback mCallback;
    private SelectedCategory.DataBean mCurrentCategoryItem;

    @Override
    public void getCategory() {
        if (mCallback != null) {
            mCallback.onLoading();
        }
        //先要拿到fenlei
        //先拿到retrofit
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<SelectedCategory> task = api.getSelectedCategory();
        task.enqueue(new Callback<SelectedCategory>() {
            @Override
            public void onResponse(Call<SelectedCategory> call, Response<SelectedCategory> response) {
                int code = response.code();
                Log.d("SelectedPagerPresenter", "code-->"+code);
                if (code == HttpURLConnection.HTTP_OK) {
                    SelectedCategory category = response.body();
                    if (mCallback != null) {
                        mCallback.onCategoryLoaded(category);
                    }
                } else {
                    if (mCallback != null) {
                        mCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<SelectedCategory> call, Throwable t) {
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        });
    }

    @Override
    public void getContentByCategory(SelectedCategory.DataBean bean) {
//        if (mCallback != null) {
//            mCallback.onLoading();
//        }
        mCurrentCategoryItem = bean;
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String url = UrlUtils.getSelectedContentUrl(bean.getFavorites_id());
        Call<SelectedContent> selectedContent = api.getSelectedContent(url);
        selectedContent.enqueue(new Callback<SelectedContent>() {
            @Override
            public void onResponse(Call<SelectedContent> call, Response<SelectedContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    SelectedContent content = response.body();
                    if (mCallback != null) {
                        mCallback.onContentLoaded(content);
                    }
                } else {
                    if (mCallback != null) {
                        mCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<SelectedContent> call, Throwable t) {
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        });
    }

    @Override
    public void reloadContent() {
//        if (mCurrentCategoryItem != null) {
//            this.getContentByCategory(mCurrentCategoryItem);
//        }
        this.getCategory();
    }

    @Override
    public void registerViewCallback(ISelectedPagerCallback callback) {
        this.mCallback = callback; //如果有多个界面需要更新，那么就使用集合保存起来，然后用for循环
    }

    @Override
    public void unregisterCallback(ISelectedPagerCallback callback) {
        mCallback = null;
    }
}
