package com.finham.taobaocoupon.presenter.implement;

import com.finham.taobaocoupon.model.Api;
import com.finham.taobaocoupon.model.domain.SearchContent;
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
    private static final int DEFAULT_PAGE = 0;
    private Api mApi;
    private ISearchPageCallback mSearchPageCallback;
    //搜索的当前页，要传入当参数用的
    private int mCurrentPage = DEFAULT_PAGE;
    private String mCurrentKeyword;

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
        //在search操作时需要动态更新状态。注意是否要动态更新状态完全由需求决定，其他操作你也可以决定是否要动态更新状态。
        if (mSearchPageCallback != null) {
            mSearchPageCallback.onLoading();
        }
        mCurrentKeyword = keyword;
        Call<SearchContent> task = mApi.search(mCurrentPage, keyword);
        task.enqueue(new Callback<SearchContent>() {
            @Override
            public void onResponse(Call<SearchContent> call, Response<SearchContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    SearchContent content = response.body();
                    if (mSearchPageCallback != null) {
                        try {
                            if (content == null || content.getData().getTbk_dg_material_optional_response().getResult_list()
                                    .getMap_data().size() == 0) {
                                mSearchPageCallback.onEmpty();
                            } else {
                                mSearchPageCallback.onSearchSuccess(content);
                            }
                        } catch (NullPointerException e) {
                            mSearchPageCallback.onEmpty();
                        }
                    }
                } else {
                    if (mSearchPageCallback != null) {
                        mSearchPageCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchContent> call, Throwable t) {
                if (mSearchPageCallback != null) {
                    mSearchPageCallback.onError();
                }
            }
        });
    }

    @Override
    public void research() {
        //重新搜索的逻辑
        if (mCurrentKeyword == null) {
            if (mSearchPageCallback != null) mSearchPageCallback.onEmpty();
        } else {
            search(mCurrentKeyword);
        }
    }

    @Override
    public void loadMore() {
        mCurrentKeyword = mCurrentKeyword + 1;
        if (mCurrentKeyword != null) {
            Call<SearchContent> task = mApi.search(mCurrentPage, mCurrentKeyword);
            task.enqueue(new Callback<SearchContent>() {
                @Override
                public void onResponse(Call<SearchContent> call, Response<SearchContent> response) {
                    int code = response.code();
                    if (code == HttpURLConnection.HTTP_OK) {
                        SearchContent body = response.body();
                        if (mSearchPageCallback != null) {
                            if (body == null || body.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data().size() == 0)
                                mSearchPageCallback.onLoadMoreEmpty();
                            else {
                                mSearchPageCallback.onLoadMoreSuccess(body);
                            }
                        }
                    } else {
                        mCurrentPage--;
                        if (mSearchPageCallback != null) mSearchPageCallback.onLoadMoreError();
                    }
                }

                @Override
                public void onFailure(Call<SearchContent> call, Throwable t) {
                    mCurrentPage--;
                    if (mSearchPageCallback != null) mSearchPageCallback.onLoadMoreError();
                }
            });
        } else {
            if (mSearchPageCallback != null) mSearchPageCallback.onEmpty();
        }
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
