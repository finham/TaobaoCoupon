package com.finham.taobaocoupon.presenter.implement;

import com.finham.taobaocoupon.model.Api;
import com.finham.taobaocoupon.model.domain.SearchRecommend;
import com.finham.taobaocoupon.presenter.ISearchPresenter;
import com.finham.taobaocoupon.utils.RetrofitManager;
import com.finham.taobaocoupon.view.ISearchPageCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * User: Fin
 * Date: 2020/5/10
 * Time: 17:21
 */
public class SearchPresenterImpl implements ISearchPresenter {
    private Api mApi;
    private ISearchPageCallback mSearchPageCallback;

    public SearchPresenterImpl() {
        RetrofitManager manager = RetrofitManager.getInstance();
        Retrofit retrofit = manager.getRetrofit();
        mApi = retrofit.create(Api.class);
    }

    @Override
    public void getHistory() {

    }

    @Override
    public void deleteHistory() {

    }

    @Override
    public void search(String keyword) {

    }

    @Override
    public void research() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getRecommendWords() {
        Call<SearchRecommend> task = mApi.getRecommendWords();
        task.enqueue(new Callback<SearchRecommend>() {
            @Override
            public void onResponse(Call<SearchRecommend> call, Response<SearchRecommend> response) {
                int code = response.code();
                //LogUtils.d(); 你最好打个Log
                if (code == HttpURLConnection.HTTP_OK) {
                    if (mSearchPageCallback != null) {
                        mSearchPageCallback.onRecommendWordsLoaded(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchRecommend> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void registerViewCallback(ISearchPageCallback callback) {
        this.mSearchPageCallback = callback;
    }

    @Override
    public void unregisterCallback(ISearchPageCallback callback) {
        this.mSearchPageCallback = null;
    }
}
