package com.finham.taobaocoupon.view;

import com.finham.taobaocoupon.model.domain.HomePagerContent;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/4/19
 * Time: 11:09
 */
public interface ICategoryPagerCallback {
    /**
     * 数据加载回来
     *
     * @param contents
     */
    void onContentLoaded(List<HomePagerContent> contents);

    //数据正在加载
    void onContentLoading(int categoryId);

    //数据出错
    void onContentError(int categoryId);

    //数据为空
    void onContentEmpty(int categoryId);

    /**
     * 加载更多时出错了
     * @param categoryId
     */
    void onLoadMoreError(int categoryId); //如果不写这个也可把上面改成void onContentError(int categoryId, boolean isRefreshed)

    /**
     * 加载更多时为空了，没有更多了
     * @param categoryId
     */
    void onLoadMoreEmpty(int categoryId); //同理

    /**
     * 加载更多时成功加载了
     * @param contents
     */
    void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents);

    /**
     * 加载轮播图
     * @param contents
     */
    void onLooperListLoaded(List<HomePagerContent.DataBean> contents);
}
