package com.finham.taobaocoupon.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseActivity;
import com.finham.taobaocoupon.model.domain.Ticket;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.utils.LogUtils;
import com.finham.taobaocoupon.utils.PresenterManager;
import com.finham.taobaocoupon.utils.UrlUtils;
import com.finham.taobaocoupon.view.ITicketCallback;

import butterknife.BindView;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 20:20
 */
public class TicketActivity extends BaseActivity implements ITicketCallback {
    ITicketPresenter ticketPresenter;

    @BindView(R.id.ticket_cover)
    public ImageView mCover; //封面图片

    @BindView(R.id.ticket_back_press)
    public View backPress;   //后退键

    @BindView(R.id.ticket_code)
    public EditText mTicketCode; //淘口令


    @BindView(R.id.ticket_copy_or_open_btn)
    public TextView mOpenOrCopyBtn; //打开按钮

//
//    @BindView(R.id.ticket_cover_loading)
//    public View loadingView;


    @BindView(R.id.ticket_load_retry)
    public View retryLoadText;   //加载出错提示

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
    protected void initListener() {
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_ticket;
    }

    @Override
    public void onTicketLoaded(String coverImage, Ticket ticket) {
        if (mCover != null && !TextUtils.isEmpty(coverImage)) { //这个方法既能判断空也能判断长度，实现也很简单可以直接进去看
            //int targetWidth = mCover.getLayoutParams().width / 2;
            LogUtils.d(TicketActivity.class, "coverImage -->" + coverImage);
            Glide.with(this).load(UrlUtils.getCoverPath(coverImage)).into(mCover);
        }

        Ticket.DataBeanX.TbkTpwdCreateResponseBean tbk_tpwd_create_response = ticket.getData().getTbk_tpwd_create_response();
        if (ticket != null && tbk_tpwd_create_response != null) {
            LogUtils.d(TicketActivity.class,"taokouling-->"+ticket.getData().getTbk_tpwd_create_response().getData().getModel());
            mTicketCode.setText(ticket.getData().getTbk_tpwd_create_response().getData().getModel());
        }
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
