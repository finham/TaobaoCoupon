package com.finham.taobaocoupon.view;

import com.finham.taobaocoupon.base.IBaseCallback;
import com.finham.taobaocoupon.model.domain.SelectedCategory;
import com.finham.taobaocoupon.model.domain.SelectedContent;

/**
 * User: Fin
 * Date: 2020/5/4
 * Time: 10:56
 */
public interface ISelectedPagerCallback extends IBaseCallback {
    /**
     * 获取精选页面的分类。目前看有六类
     * @param category
     */
    void onCategoryLoaded(SelectedCategory category);

    /**
     * 获取精选页面每个分类的内容。
     * @param content
     */
    void  onContentLoaded(SelectedContent content);
}
