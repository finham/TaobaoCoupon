package com.finham.taobaocoupon.presenter;

import com.finham.taobaocoupon.base.IBasePresenter;
import com.finham.taobaocoupon.model.domain.SelectedCategory;
import com.finham.taobaocoupon.view.ISelectedPagerCallback;

/**
 * User: Fin
 * Date: 2020/5/4
 * Time: 10:54
 */
public interface ISelectedPagerPresenter extends IBasePresenter<ISelectedPagerCallback> {

    /**
     * 获取分类
     */
    void getCategory();

    /**
     * 根据分类获取内容
     * @param bean
     */
    void getContentByCategory(SelectedCategory.DataBean bean); //最重要的就是其中的DataBean类！

    /**
     * 重新加载content
     */
    void reloadContent();
}
