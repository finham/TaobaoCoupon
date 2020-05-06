package com.finham.taobaocoupon.presenter.implement;

import com.finham.taobaocoupon.model.Api;
import com.finham.taobaocoupon.model.domain.PreferentialContent;
import com.finham.taobaocoupon.presenter.IPreferentialPagePresenter;
import com.finham.taobaocoupon.utils.RetrofitManager;
import com.finham.taobaocoupon.utils.UrlUtils;
import com.finham.taobaocoupon.view.IPreferentialPageCallback;

import java.net.HttpURLConnection;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * User: Fin
 * Date: 2020/5/6
 * Time: 13:57
 */
public class PreferentialPagePresenterImpl implements IPreferentialPagePresenter {
    private static final int DEFAULT_PAGE = 1;
    private int mCurrentPage = DEFAULT_PAGE;
    private IPreferentialPageCallback mCallback;
    private boolean isLoading = false; //防御性功能？听不太懂。为了逻辑比较严谨，比如你连续点了多次加载更多，不应该让它多次去加载吧！

    @Override
    public void getPreferentialContent() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        if (mCallback != null) {
            mCallback.onLoading();
        }
        //获取特惠内容
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String url = UrlUtils.getPreferentialPath(mCurrentPage);
        Call<PreferentialContent> task = api.getPreferentialContent(url);
        task.enqueue(new Callback<PreferentialContent>() {
            @Override
            public void onResponse(Call<PreferentialContent> call, Response<PreferentialContent> response) {
                isLoading = false;
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    PreferentialContent body = response.body();
                    if (mCallback != null) {
                        try {
                            if (Objects.requireNonNull(body).getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size() == 0) {
                                mCallback.onEmpty();
                            } else {
                                mCallback.onContentLoaded(body);
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            mCallback.onEmpty();
                        }
                    }
                } else {
                    if (mCallback != null) {
                        mCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<PreferentialContent> call, Throwable t) {
                isLoading = false;
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        });

    }

    @Override
    public void reload() {
        //重新加载其实就是再调用一次
        this.getPreferentialContent();
    }

    /**
     * 加载更多，通知UI更新
     */
    @Override
    public void loadMore() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        //加载更多主要是管理Page的页码
        mCurrentPage++;  //https://zhidao.baidu.com/question/84652577.html?sort=11&rn=5&pn=0#wgt-answers mCurrentPage = ...+1
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String url = UrlUtils.getPreferentialPath(mCurrentPage);
        Call<PreferentialContent> loadMoreTask = api.getPreferentialContent(url);
        loadMoreTask.enqueue(new Callback<PreferentialContent>() {
            @Override
            public void onResponse(Call<PreferentialContent> call, Response<PreferentialContent> response) {
                isLoading = false;
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    PreferentialContent content = response.body();
                    try {
                        if (Objects.requireNonNull(content).getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size() == 0) {
                            mCallback.onLoadMoreEmpty();
                        } else {
                            mCallback.onLoadMore(content);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        mCallback.onLoadMoreEmpty();
                    }
                } else {
                    if (mCallback != null) {
                        mCurrentPage--;
                        mCallback.onLoadMoreError();
                    }
                }
            }

            @Override
            public void onFailure(Call<PreferentialContent> call, Throwable t) {
                isLoading = false;
                if (mCallback != null) {
                    mCurrentPage--;
                    mCallback.onLoadMoreError();
                }
            }
        });
    }

    //UI层的回调需要在这里赋值！
    @Override
    public void registerViewCallback(IPreferentialPageCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void unregisterCallback(IPreferentialPageCallback callback) {
        if (mCallback != null) {
            mCallback = null;
        }
    }
}
