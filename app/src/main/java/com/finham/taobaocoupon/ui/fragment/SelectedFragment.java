package com.finham.taobaocoupon.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.SelectedCategory;
import com.finham.taobaocoupon.model.domain.SelectedContent;
import com.finham.taobaocoupon.presenter.ISelectedPagerPresenter;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.ui.activity.TicketActivity;
import com.finham.taobaocoupon.ui.adapter.SelectedCategoryAdapter;
import com.finham.taobaocoupon.ui.adapter.SelectedContentAdapter;
import com.finham.taobaocoupon.utils.DensityUtils;
import com.finham.taobaocoupon.utils.PresenterManager;
import com.finham.taobaocoupon.view.ISelectedPagerCallback;

import butterknife.BindView;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 13:34
 */
public class SelectedFragment extends BaseFragment implements ISelectedPagerCallback, SelectedCategoryAdapter.onLeftCategoryClickListener, SelectedContentAdapter.onRightContentClickListener {
    @BindView(R.id.left_category_list)
    public RecyclerView mLeft;
    @BindView(R.id.right_content_list)
    public RecyclerView mRight;

    ISelectedPagerPresenter mSelectedPagerPresenter;
    private SelectedCategoryAdapter mLeftAdapter;
    private SelectedContentAdapter mRightAdapter;


    @Override
    protected void initView(View view) {
        changeState(State.SUCCESS);
        //左边的RV设置数据并显示
        mLeft.setLayoutManager(new LinearLayoutManager(requireContext()));
        mLeftAdapter = new SelectedCategoryAdapter();
        mLeft.setAdapter(mLeftAdapter);

        mRight.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRightAdapter = new SelectedContentAdapter();
        mRight.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtils.dp2px(requireContext(), 4);
                outRect.bottom = DensityUtils.dp2px(requireContext(), 4);
                outRect.left = DensityUtils.dp2px(requireContext(), 6);
                outRect.right = DensityUtils.dp2px(requireContext(), 6);
            }
        });
        mRight.setAdapter(mRightAdapter);
    }

    @Override
    protected void initListener() {
        mLeftAdapter.setLeftCategoryClickListener(this);
        mRightAdapter.setOnRightContentClickListener(this);
    }

    @Override
    protected void initPresenter() {
        mSelectedPagerPresenter = PresenterManager.getInstance().getSelectedPagerPresenter();
        mSelectedPagerPresenter.registerViewCallback(this); //让当前类实现该接口，也就相当于注册了回调！妙哉
        //少了以下这句至少调试了20分钟= =。。。 MVP我真的很容易乱糟糟
        mSelectedPagerPresenter.getCategory();
    }

    @Override
    protected void release() {
        if (mSelectedPagerPresenter != null) {
            mSelectedPagerPresenter.unregisterCallback(this);
        }
    }

    @Override
    protected void onRetryClick() {
        if (mSelectedPagerPresenter != null) {
            mSelectedPagerPresenter.reloadContent();
        }
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selected;
    }

    @Override
    public void onCategoryLoaded(SelectedCategory category) {
        changeState(State.SUCCESS);
        mLeftAdapter.setData(category);
        //分类的数据会从这个方法传回来！分类的操作要在这里做
        //根据当前的分类，然后再去拿内容数据
//        List<SelectedCategory.DataBean> contents = category.getData();
//        mSelectedPagerPresenter.getContentByCategory(contents.get(0));
    }

    @Override
    public void onContentLoaded(SelectedContent content) {
        //上面写那两句后来这边看看数据是否正确回来
        mRightAdapter.setData(content);
        //每次切换都回到第一个位置
        mRight.scrollToPosition(0);
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
    public void onLeftCategoryClick(SelectedCategory.DataBean bean) {
        //在此处设置左边分类的点击事件
        mSelectedPagerPresenter.getContentByCategory(bean);
    }

    @Override
    public void onRightContentClick(SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean data) {
        //跳转到淘口令页面
        String title = data.getTitle();
        String url = data.getCoupon_click_url();
        if (TextUtils.isEmpty(url)) {
            url = data.getClick_url(); //有一些商品可能没有券，那么就得做这样的预防case
        }
        String cover = data.getPict_url();
        ITicketPresenter ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.getTicket(title, url, cover);
        startActivity(new Intent(requireActivity(), TicketActivity.class));
    }
}
