package com.finham.taobaocoupon.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.finham.taobaocoupon.model.domain.IBaseInfo;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.ui.activity.TicketActivity;

/**
 * User: Fin
 * Date: 2020/5/8
 * Time: 22:13
 */
public class TicketUtils {
    public static void toTicketPage(Context context, IBaseInfo info) {
        String title = info.getTitle();
        //这是商品详情的地址，我们要的是领券的地址
        //String url = item.getClick_url();
        //这个url就是领券的地址
        String url = info.getUrl();
        if (TextUtils.isEmpty(url)) {
            url = info.getUrl(); //有一些商品可能没有券，那么就得做这样的预防case
        }
        String cover = info.getCover();
        ITicketPresenter ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.getTicket(title, url, cover);
        context.startActivity(new Intent(context, TicketActivity.class));
        //因为这个Context生命周期比较长，所以不存在内存泄漏的问题！（好像确实是这样的，但是用Application的Context比较少欸。。）
        //还是乖乖用Context参数比较好。当然照老师说法也是可以的
    }
}
