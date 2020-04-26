package com.finham.taobaocoupon.presenter;

import com.finham.taobaocoupon.base.IBasePresenter;
import com.finham.taobaocoupon.view.ITicketCallback;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 22:09
 */
public interface ITicketPresenter extends IBasePresenter<ITicketCallback> {
    /**
     * 只需要一个方法也就是获取ticket的淘口令
     * @param title
     * @param url
     * @param coverImage
     */
    void getTicket(String title,String url,String coverImage); //你要把整个data传过去也行= =就是不太好
}
