package com.finham.taobaocoupon.view;

import com.finham.taobaocoupon.base.IBaseCallback;
import com.finham.taobaocoupon.model.domain.Category;

/**
 * User: Fin
 * Date: 2020/4/17
 * Time: 11:37
 */
//当从网络取到数据时，需要通知UI进行更新，这里的接口与方法都是跟UI相关的
public interface IHomeCallback extends IBaseCallback {
    void onCategoriesLoaded(Category category);
}
