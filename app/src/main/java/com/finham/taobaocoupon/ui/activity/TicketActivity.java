package com.finham.taobaocoupon.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.finham.taobaocoupon.utils.ToastUtils;
import com.finham.taobaocoupon.utils.UrlUtils;
import com.finham.taobaocoupon.view.ITicketCallback;

import java.util.Objects;

import butterknife.BindView;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 20:20
 */
public class TicketActivity extends BaseActivity implements ITicketCallback {
    ITicketPresenter ticketPresenter;
    private boolean isTaobaoInstalled = false;

    @BindView(R.id.ticket_cover)
    public ImageView mCover; //封面图片

    @BindView(R.id.ticket_back_press)
    public View backPress;   //后退键

    @BindView(R.id.ticket_code)
    public EditText mTicketCode; //淘口令


    @BindView(R.id.ticket_copy_or_open_btn)
    public TextView mOpenOrCopyBtn; //打开按钮


    @BindView(R.id.ticket_cover_loading)
    public View loadingView;


    @BindView(R.id.ticket_load_retry)
    public View retryLoadText;   //加载出错提示

    @Override
    protected void initPresenter() {
        ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        if (ticketPresenter != null) {
            ticketPresenter.registerViewCallback(this);  //实现接口并实现回调方法
        }
        //判断是否安装淘宝 act=android.intent.action.main | cat = [android.intent.category.LAUNCHER]
        //flag = 0x10200000 | cmp =com.taobao.taobao/com.taobao.tao.welcome.Welcome 可得包名为com.taobao.taobao
        //= = 其实直接打开手机看应用详细信息就能获取到了，不用这么麻烦
        //检查是否安装有淘宝
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.taobao.taobao", PackageManager.MATCH_UNINSTALLED_PACKAGES);//即使你卸载了，我也要拿到你的信息
            isTaobaoInstalled = packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            isTaobaoInstalled = false;
        }
        LogUtils.d(TicketActivity.class, "isTaobaoInstalled-->" + isTaobaoInstalled);
        //根据这个布尔值去修改UI
        updateButtonText();
    }

    private void updateButtonText() {
        mOpenOrCopyBtn.setText(isTaobaoInstalled ? "打开淘宝领券" : "复制淘口令");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        backPress.setOnClickListener(view -> finish());
        mOpenOrCopyBtn.setOnClickListener(view -> {
            //复制淘口令，如果有安装淘宝则打开淘宝
            String code = mTicketCode.getText().toString().trim();
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("taobao_ticket_code", code);//第一个参数为label标签
            Objects.requireNonNull(cm).setPrimaryClip(data);
            //判断有没有淘宝
            if (isTaobaoInstalled) {
                Intent taobaoIntent = new Intent();
                taobaoIntent.setComponent(new ComponentName("com.taobao.taobao", "com.taobao.tao.welcome.Welcome"));
                startActivity(taobaoIntent);
            } else {
                ToastUtils.showToast("淘口令已复制！");
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_ticket;
    }

    @Override
    public void onTicketLoaded(String coverImage, Ticket ticket) {
        if (retryLoadText != null) {
            retryLoadText.setVisibility(View.GONE);
        }
        if (mCover != null && !TextUtils.isEmpty(coverImage)) { //这个方法既能判断空也能判断长度，实现也很简单可以直接进去看
            //int targetWidth = mCover.getLayoutParams().width / 2;
            LogUtils.d(TicketActivity.class, "coverImage -->" + coverImage);
            Glide.with(this).load(UrlUtils.getCoverPath(coverImage)).into(mCover);
        }

        Ticket.DataBeanX.TbkTpwdCreateResponseBean tbk_tpwd_create_response = ticket.getData().getTbk_tpwd_create_response();
        if (ticket != null && tbk_tpwd_create_response != null) {
            LogUtils.d(TicketActivity.class, "taokouling-->" + ticket.getData().getTbk_tpwd_create_response().getData().getModel());
            mTicketCode.setText(ticket.getData().getTbk_tpwd_create_response().getData().getModel());
        }
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (retryLoadText != null) {
            retryLoadText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoading() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
        if (retryLoadText != null) {
            retryLoadText.setVisibility(View.GONE);
        }
    }

    @Override //因为这个界面没做EMPTY状态，所以这个就不写了
    public void onEmpty() {
    }

    @Override
    protected void release() {
        if (ticketPresenter != null) {
            ticketPresenter.unregisterCallback(this);
        }
    }
}
