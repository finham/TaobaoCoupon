package com.finham.taobaocoupon.presenter;

import com.finham.taobaocoupon.base.IBasePresenter;
import com.finham.taobaocoupon.view.ISearchViewCallback;

/**
 * User: Fin
 * Date: 2020/5/10
 * Time: 13:21
 */
public interface ISearchPresenter extends IBasePresenter<ISearchViewCallback> {
    //获取历史搜索记录
    void getHistory();
    //删除历史搜索记录
    void deleteHistory();
    //搜索
    void search(String keyword);
    //重新搜索
    void  research();
    //获取更多搜索结果
    void loadMore();
    //如果没有搜索历史，那么应该放推荐词
    void getRecommendWords();
}