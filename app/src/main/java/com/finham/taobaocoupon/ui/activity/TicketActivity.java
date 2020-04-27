package com.finham.taobaocoupon.ui.activity;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseActivity;
import com.finham.taobaocoupon.model.domain.Ticket;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.utils.PresenterManager;
import com.finham.taobaocoupon.view.ITicketCallback;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 20:20
 */
public class TicketActivity extends BaseActivity implements ITicketCallback {
    ITicketPresenter ticketPresenter;
    @Override
    protected void initPresenter() {
        ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        if (ticketPresenter != null) {
            ticketPresenter.registerViewCallback(this);  //实现接口并实现回调方法
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_ticket;
    }

    @Override
    public void onTicketLoaded(String coverImage, Ticket ticket) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    protected void release() {
        if (ticketPresenter != null) {
            ticketPresenter.unregisterCallback(this);
        }
    }
}
