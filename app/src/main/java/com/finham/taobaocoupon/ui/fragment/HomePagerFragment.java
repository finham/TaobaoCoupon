package com.finham.taobaocoupon.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.Category;
import com.finham.taobaocoupon.model.domain.HomePagerContent;
import com.finham.taobaocoupon.presenter.ICategoryPagerPresenter;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.ui.activity.TicketActivity;
import com.finham.taobaocoupon.ui.adapter.HomePagerRecyclerViewAdapter;
import com.finham.taobaocoupon.ui.adapter.LooperAdapter;
import com.finham.taobaocoupon.ui.custom.AutoLoopViewPager;
import com.finham.taobaocoupon.utils.Constants;
import com.finham.taobaocoupon.utils.DensityUtils;
import com.finham.taobaocoupon.utils.LogUtils;
import com.finham.taobaocoupon.utils.PresenterManager;
import com.finham.taobaocoupon.utils.ToastUtils;
import com.finham.taobaocoupon.view.ICategoryPagerCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.view.TaobaoNestedScrollView;

import java.util.List;

import butterknife.BindView;

/**
 * User: Fin
 * Date: 2020/4/18
 * Time: 14:32
 */
public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private int mMaterialId;

    @BindView(R.id.home_pager_recyclerview)
    public RecyclerView mRecyclerView;
    @BindView(R.id.looper)
    public AutoLoopViewPager mViewPager;

    @BindView(R.id.home_pager_title) //重启就能找到id了= =
    public TextView mTextView;
    @BindView(R.id.looper_point_container)
    public LinearLayout mPointContainer;
    @BindView(R.id.home_pager_refresh)
    public TwinklingRefreshLayout mTwinklingRefreshLayout;

    @BindView(R.id.home_pager_parent)
    public LinearLayout mHomePagerParent;
    @BindView(R.id.tb_scroller)
    public TaobaoNestedScrollView mTaobaoNestedScrollView;
    @BindView(R.id.header_container)
    public LinearLayout mHeaderContainer;
    private HomePagerRecyclerViewAdapter mAdapter;
    private LooperAdapter mLooperAdapter;

    /*将category传进来，然后创建对应的HomePagerFragment！*/
    public static HomePagerFragment newInstance(Category.DataBean bean) {
        HomePagerFragment fragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.HOME_PAGER_KEY_TITLE, bean.getTitle());
        bundle.putInt(Constants.HOME_PAGER_KEY_MATERIAL_ID, bean.getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //可见时候开始loop
        mViewPager.startLoop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewPager.stopLoop();
    }

    //继承于BaseFragment，那么那些变量也就都继承过来了。
    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View view) {
        //先拿到ViewTree的观察者，然后添加一个全局的布局的监听
        mHomePagerParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() { //该方法会频繁调用的
                if (mHeaderContainer == null) {
                    return;
                }
                int headerHeight = mHeaderContainer.getMeasuredHeight();
                mTaobaoNestedScrollView.setHeight(headerHeight);
                int height = mHomePagerParent.getMeasuredHeight();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
                params.height = height;
                mRecyclerView.setLayoutParams(params);
                //不想让这个方法频繁调用
                if (height != 0) {
                    mHomePagerParent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;    //这里的单位是px，后面我们把它转为dp
                outRect.bottom = 8;
            }
        });
        //RV的适配器
        mAdapter = new HomePagerRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //VP的适配器
        mLooperAdapter = new LooperAdapter();
        mViewPager.setAdapter(mLooperAdapter);
        //设置Refresh相关内容
        mTwinklingRefreshLayout.setEnableRefresh(false);//上拉刷新取消掉
        mTwinklingRefreshLayout.setEnableLoadmore(true);//下拉加载更多保留
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(dataBean -> {
            //RecyclerView内容被点击
            ToastUtils.showToast(dataBean.getTitle());
            handleItemClick(dataBean);
        });

        //轮播图被点击
        mLooperAdapter.setOnLooperClickListener(this::handleItemClick);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //防止异常：ArithmeticException：divide by zero
                if (mLooperAdapter.getDataSize() == 0) {
                    return;
                }
                int realPosition = position % mLooperAdapter.getDataSize();
                //主要在该方法中要切换小圆点指示器
                changePointIndicator(realPosition);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                LogUtils.d(getClass(), "触发了loadMore");
                //加载更多内容
                if (mCategoryPagerPresenter != null) {
                    mCategoryPagerPresenter.loadMore(mMaterialId);
                }
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.getMeasuredHeight();
                mRecyclerView.getHeight();
            }
        });
    }

    private void handleItemClick(HomePagerContent.DataBean item) {
        String title = item.getTitle();
        String url = item.getClick_url();
        String cover = item.getPict_url();
        ITicketPresenter ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.getTicket(title, url, cover);
        startActivity(new Intent(requireActivity(), TicketActivity.class));
    }

    /**
     * 切换小圆点指示器
     *
     * @param realPosition
     */
    private void changePointIndicator(int realPosition) {
        for (int i = 0; i < mPointContainer.getChildCount(); i++) {
            View point = mPointContainer.getChildAt(i);
            if (i == realPosition) {
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
        }
    }

    @Override
    protected void initPresenter() {
//        mCategoryPagerPresenter = CategoryPagerPresenterImpl.getInstance(); //通过接口获取实例，隐藏内部实现！妙
        mCategoryPagerPresenter = PresenterManager.getInstance().getCategoryPagerPresenter();
        mCategoryPagerPresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {  //loadData()是在loadView()之后的
        String title = getArguments().getString(Constants.HOME_PAGER_KEY_TITLE);
        mMaterialId = getArguments().getInt(Constants.HOME_PAGER_KEY_MATERIAL_ID);

        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.getContentByCategoryId(mMaterialId);
        }
        if (mTextView != null) {
            mTextView.setText(title);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> contents) {
//        if(mMaterialId != categoryId) return;
        changeState(State.SUCCESS);
        mAdapter.setData(contents);
    }

    @Override
    public void onLoading() {
        changeState(State.LOADING);
    }

    @Override
    public void onError() {
        //网络错误！
        changeState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        changeState(State.EMPTY);
    }

    @Override
    public int getCategoryId() {
        return mMaterialId;
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.showToast("网络异常");
        if (mTwinklingRefreshLayout != null) mTwinklingRefreshLayout.finishLoadmore();
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtils.showToast("没有更多商品");
        if (mTwinklingRefreshLayout != null) mTwinklingRefreshLayout.finishLoadmore();
    }

    @Override
    public void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents) {
        //添加到RecyclerView数据的底部
        mAdapter.addData(contents);
        //添加进去后要结束refresh的动画
        if (mTwinklingRefreshLayout != null) //还是得判空，因为加载时用户可能会返回桌面等等的。虽然不影响用户体验，但是你还是得避免
            mTwinklingRefreshLayout.finishLoadmore();
        ToastUtils.showToast("更新了" + contents.size() + "件商品");
    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        mLooperAdapter.setData(contents);
        //在数据加载好了后添加小圆点。因为这是跟UI相关的代码，所以在View层写是没关系的。MVP中的View
        mPointContainer.removeAllViews();
        //GradientDrawable selected = (GradientDrawable) getContext().getDrawable(R.drawable.shape_indicator_point_selected);
        //GradientDrawable normal = (GradientDrawable) getContext().getDrawable(R.drawable.shape_indicator_point_normal);

        //中间点%size不一定为0，所以不一定为第一个，所以改写一下：
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % contents.size());
        for (int i = 0; i < contents.size(); i++) {
            View point = new View(getContext());
            //单位为px像素单位，要转为dp。你直接填切图上的8肯定是不对的，因为单位不同。
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dp2px(getContext(), 8), DensityUtils.dp2px(getContext(), 8));
            params.leftMargin = DensityUtils.dp2px(getContext(), 5);
            params.rightMargin = DensityUtils.dp2px(getContext(), 5);
            point.setLayoutParams(params);
            if (i == 0) {
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
            mPointContainer.addView(point);
        }
    }

    @Override
    protected void release() {
        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.unregisterCallback(this);
        }
    }
}
