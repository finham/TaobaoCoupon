package com.finham.taobaocoupon.view;

import com.finham.taobaocoupon.base.IBaseCallback;
import com.finham.taobaocoupon.model.domain.PreferentialContent;

/**
 * User: Fin
 * Date: 2020/5/5
 * Time: 22:23
 */
public interface IPreferentialPageCallback extends IBaseCallback {
    /**
     * 特惠内容加载成功
     * @param content
     */
    void onContentLoaded(PreferentialContent content);

    /**
     * 加载更多特惠内容
     * @param moreContent
     */
    void onLoadMore(PreferentialContent moreContent);

    /**
     * 加载更多出错
     * 注意！加载更多出错的话不能调用onError，否则会将之前加载好的数据也给清理掉
     */
    void onLoadMoreError();

    void onLoadMoreEmpty();
}
