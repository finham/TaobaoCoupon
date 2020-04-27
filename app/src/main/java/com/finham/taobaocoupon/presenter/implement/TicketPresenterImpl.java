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
    @Override
    public void getTicket(String title, String url, String coverImage) { //传这三个参数是因为要放在页面上，同时对于获取淘口令也是必要的
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
                    Ticket body = response.body();
                } else {

                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {

            }
        });
    }

    @Override
    public void registerViewCallback(ITicketCallback callback) {

    }

    @Override
    public void unregisterCallback(ITicketCallback callback) {

    }
}
