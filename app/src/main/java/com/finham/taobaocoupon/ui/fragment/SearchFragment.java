package com.finham.taobaocoupon.ui.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.SearchContent;
import com.finham.taobaocoupon.model.domain.SearchRecommend;
import com.finham.taobaocoupon.presenter.ISearchPresenter;
import com.finham.taobaocoupon.utils.PresenterManager;
import com.finham.taobaocoupon.view.ISearchPageCallback;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 13:34
 */
public class SearchFragment extends BaseFragment implements ISearchPageCallback {


    private ISearchPresenter mSearchPresenter;

    @Override
    protected void initPresenter() {
        mSearchPresenter = PresenterManager.getInstance().getSearchPresenter();
        mSearchPresenter.registerViewCallback(this);
        //获取搜索推荐词
        mSearchPresenter.getRecommendWords();
        mSearchPresenter.search("毛衣");
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.search_fragment_layout, container, false);
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View view) {
        changeState(State.SUCCESS);
    }

    @Override
    public void onHistoryLoaded(List<String> histories) {

    }

    @Override
    public void onHistoryDeleted(String history) {

    }

    @Override
    public void onSearchSuccess(SearchContent content) {

    }

    @Override
    public void onLoadMoreSuccess(SearchContent content) {

    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onRecommendWordsLoaded(List<SearchRecommend.DataBean> words) {
        //推荐词从这个回调回来！
        Log.d("SearchFragment","size-->"+words.size());
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    protected void release() {
        if (mSearchPresenter != null) mSearchPresenter.unregisterCallback(this);
    }
}
