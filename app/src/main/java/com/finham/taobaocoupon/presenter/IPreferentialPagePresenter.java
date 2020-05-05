package com.finham.taobaocoupon.presenter;

import com.finham.taobaocoupon.base.IBasePresenter;
import com.finham.taobaocoupon.view.IPreferentialPageCallback;

/**
 * User: Fin
 * Date: 2020/5/5
 * Time: 22:22
 */
public interface IPreferentialPagePresenter extends IBasePresenter<IPreferentialPageCallback> {
    /**
     * 获取特惠内容
     */
    void getPreferentialContent();

    /**
     * 重新加载特惠内容
     * 更智能点就是去监听网络变化
     */
    void reload();

    /**
     * 加载更多特惠内容
     */
    void loadMore();
}
