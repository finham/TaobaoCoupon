package com.finham.taobaocoupon.presenter.implement;

import com.finham.taobaocoupon.model.Api;
import com.finham.taobaocoupon.model.domain.Ticket;
import com.finham.taobaocoupon.model.domain.TicketParams;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.utils.RetrofitManager;
import com.finham.taobaocoupon.utils.UrlUtils;
import com.finham.taobaocoupon.view.ITicketCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 22:22
 */
public class TicketPresenterImpl implements ITicketPresenter {
    private ITicketCallback mCallback;
    private String mCoverImage;
    Ticket mTicket;

    /**
     * 这里有一个情况：点击后getTicket很快很快，快到主界面还没有把callback给注册（就是还为空的状态）
     * 但是这时候数据已经过来了！这样子就没法更新UI了。
     * 就是为了处理mCallback为空的情况！
     */
    enum LoadState {
        LOADING, SUCCESS, ERROR, NONE
    }

    private LoadState mCurrent = LoadState.NONE;

    @Override
    public void getTicket(String title, String url, String coverImage) { //传这三个参数是因为要放在页面上，同时对于获取淘口令也是必要的
        onTicketLoading();
        this.mCoverImage = coverImage;
        //获取淘口令
        String ticketUrl = UrlUtils.getTicketUrl(url);
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        TicketParams params = new TicketParams(ticketUrl, title);
        Call<Ticket> task = api.getTicketContent(params);
        task.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    mTicket = response.body();
                    //无论请求成功还是失败都要通知界面更新UI
                    //注意处处都需要判空= =，这就是Java的迷人之处
                    onTicketLoadSuccess();
                } else {
                    onTicketLoadError();
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                onTicketLoadError();
            }
        });
    }

    private void onTicketLoading() {
        if (mCallback != null)
            mCallback.onLoading();
        else
            mCurrent = LoadState.LOADING;
    }

    private void onTicketLoadSuccess() {
        if (mCallback != null)
            mCallback.onTicketLoaded(mCoverImage, mTicket);
        else
            mCurrent = LoadState.SUCCESS;
    }

    private void onTicketLoadError() {
        if (mCallback != null)
            mCallback.onError();
        else
            mCurrent = LoadState.ERROR;
    }

    @Override
    public void registerViewCallback(ITicketCallback callback) {
        //因为这里只有一个页面需要通知状态，所以一个就行了。
        // 如果是多个界面，那么就用集合保存，跟category一样的。
        if (mCurrent != LoadState.NONE) {
            //说明状态已经改变了，更细UI。用来处理getTicket非常快的情况
            if (mCurrent == LoadState.SUCCESS) {
                onTicketLoadSuccess();
            } else if (mCurrent == LoadState.ERROR) {
                onTicketLoadError();
            } else if (mCurrent == LoadState.LOADING) {
                onTicketLoading();
            }
        }
        this.mCallback = callback;
    }

    @Override
    public void unregisterCallback(ITicketCallback callback) {
        mCallback = null;
    }
}
