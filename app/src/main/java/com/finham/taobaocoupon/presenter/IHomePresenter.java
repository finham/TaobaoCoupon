package com.finham.taobaocoupon.presenter;

import com.finham.taobaocoupon.view.IHomeCallback;

/**
 * User: Fin
 * Date: 2020/4/17
 * Time: 11:24
 */
//编写需要的接口，定义方法。
public interface IHomePresenter {
    /*获取商品分类*/
    void getCategories();

    /*因为是异步的，你需要去网络访问去取数据，所以你需要等，在view包中创建接口通知UI更新*/
    //注册UI回调
    void registerCallback(IHomeCallback callback);
    //取消UI回调
    void unregisterCallback(IHomeCallback callback);
}
