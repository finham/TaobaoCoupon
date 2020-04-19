package com.finham.taobaocoupon.presenter;

/**
 * User: Fin
 * Date: 2020/4/19
 * Time: 10:52
 */
public interface ICategoryPagerPresenter {
    /**
     * 根据分类id获取内容
     *
     * @param categoryId
     */
    void getContentByCategoryId(int categoryId);

    /**
     * 还是需要根据不同的分类来加载更多
     *
     * @param categoryId
     */
    void loadMore(int categoryId);

    void reload(int categoryId);
}
