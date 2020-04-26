package com.finham.taobaocoupon.view;

import com.finham.taobaocoupon.base.IBaseCallback;
import com.finham.taobaocoupon.model.domain.Ticket;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 22:10
 */
public interface ITicketCallback extends IBaseCallback {
    /**
     * 淘口令加载结果
     * @param coverImage
     * @param ticket
     */
    void onTicketLoaded(String coverImage, Ticket ticket);
}
