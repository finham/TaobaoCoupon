package com.finham.taobaocoupon.view;

import com.finham.taobaocoupon.base.IBaseCallback;
import com.finham.taobaocoupon.model.domain.SearchContent;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/5/10
 * Time: 13:24
 */
public interface ISearchViewCallback extends IBaseCallback {
    //写一个View的callback之前你要先想想有什么时候需要改变界面UI的操作和时刻，然后写对应的代码即可！

    //历史搜索已加载（更完善的话应该考虑摆放的优先级等等，这里按最简单的处理）
    void onHistoryLoaded(List<String> histories);

    //历史搜索被删除时肯定也要更新UI呀！所以也要在接口里定义一个描述该操作后的方法
    void onHistoryDeleted(String history);

    //搜索成功！至于出错、为空、加载中都在IBaseCallback中定义了
    void onSearchSuccess(SearchContent content);

    //加载更多结果
    void onLoadMoreSuccess(SearchContent content);

    void onLoadMoreError();

    void onLoadMoreEmpty();

    void onRecommendWordsLoaded(List<String> words);
}
