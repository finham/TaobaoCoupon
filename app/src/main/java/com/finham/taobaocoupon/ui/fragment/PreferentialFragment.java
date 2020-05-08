package com.finham.taobaocoupon.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.PreferentialContent;
import com.finham.taobaocoupon.presenter.IPreferentialPagePresenter;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.ui.activity.TicketActivity;
import com.finham.taobaocoupon.ui.adapter.PreferentialAdapter;
import com.finham.taobaocoupon.utils.PresenterManager;
import com.finham.taobaocoupon.utils.ToastUtils;
import com.finham.taobaocoupon.view.IPreferentialPageCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;

import butterknife.BindView;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 14:34
 */
public class PreferentialFragment extends BaseFragment implements IPreferentialPageCallback, PreferentialAdapter.OnPreferentialItemClickListener {

    IPreferentialPagePresenter mPreferentialPagePresenter;
    private PreferentialAdapter mAdapter;

    @BindView(R.id.preferential_content_list)
    public RecyclerView mRecyclerView;
    @BindView(R.id.preferential_refresh_layout)
    public TwinklingRefreshLayout mTwinklingRefreshLayout;

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.title_fragment_layout,container,false);
    }

    @Override
    protected void initView(View view) {
        mAdapter = new PreferentialAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = DensityUtil.dp2px(requireContext(), 2.5f);
                outRect.bottom = DensityUtil.dp2px(requireContext(), 2.5f);
                outRect.left = DensityUtil.dp2px(requireContext(), 2.5f);
                outRect.right = DensityUtil.dp2px(requireContext(), 2.5f);
            }
        });
        mTwinklingRefreshLayout.setEnableLoadmore(true);
        mTwinklingRefreshLayout.setEnableRefresh(false); //不让它支持下拉刷新
        mTwinklingRefreshLayout.setEnableOverScroll(true);
    }

    @Override
    protected void initListener() {
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //加载更多的逻辑
                if (mPreferentialPagePresenter != null) {
                    mPreferentialPagePresenter.loadMore(); //这边去加载更多后，结果会从onLoadMore方法中返回。
                }
            }
        });
        mAdapter.setOnPreferentialItemClickListener(this);
    }

    @Override
    protected void initPresenter() {
        mPreferentialPagePresenter = PresenterManager.getInstance().getPreferentialPagePresenter();
        mPreferentialPagePresenter.registerViewCallback(this);
        mPreferentialPagePresenter.getPreferentialContent(); //拿到数据后就直接跳转到回调方法 onContentLoaded()
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_preferential;
    }

    @Override
    protected void release() {
        if (mPreferentialPagePresenter != null) {
            mPreferentialPagePresenter.unregisterCallback(this);
        }
    }

    @Override
    public void onContentLoaded(PreferentialContent content) {
        changeState(State.SUCCESS);
        //数据从这个方法回来了！在此更新UI
        mAdapter.setData(content);
    }

    @Override
    public void onLoadMore(PreferentialContent moreContent) {
        mTwinklingRefreshLayout.finishLoadmore();
        mAdapter.setMoreData(moreContent);
    }

    @Override
    public void onLoadMoreError() {
        mTwinklingRefreshLayout.finishLoadmore();
        ToastUtils.showToast("网络异常");
    }

    @Override
    public void onLoadMoreEmpty() {
        mTwinklingRefreshLayout.finishLoadmore();
        ToastUtils.showToast("没有更多内容");
    }

    @Override
    public void onError() {
        changeState(State.ERROR);
    }

    @Override
    public void onLoading() {
        changeState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        changeState(State.EMPTY);
    }

    @Override
    public void onPreferentialItemClick(PreferentialContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean item) {
        String title = item.getTitle();
        //这是商品详情的地址，我们要的是领券的地址
        //String url = item.getClick_url();
        //这个url就是领券的地址
        String url = item.getCoupon_click_url();
        if (TextUtils.isEmpty(url)) {
            url = item.getClick_url(); //有一些商品可能没有券，那么就得做这样的预防case
        }
        String cover = item.getPict_url();
        ITicketPresenter ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.getTicket(title, url, cover);
        startActivity(new Intent(requireActivity(), TicketActivity.class));
    }
}
