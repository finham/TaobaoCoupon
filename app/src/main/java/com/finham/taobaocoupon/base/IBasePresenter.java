package com.finham.taobaocoupon.base;

/**
 * User: Fin
 * Date: 2020/4/20
 * Time: 10:03
 */
public interface IBasePresenter<T> {

    /*因为是异步的，你需要去网络访问去取数据，所以你需要等，在view包中创建接口通知UI更新*/
    //注册UI回调
    void registerViewCallback(T callback);
    //取消UI回调
    void unregisterCallback(T callback);
}
