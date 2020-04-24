package com.finham.taobaocoupon.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
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
import com.finham.taobaocoupon.presenter.implement.CategoryPagerPresenterImpl;
import com.finham.taobaocoupon.ui.adapter.HomePagerRecyclerViewAdapter;
import com.finham.taobaocoupon.ui.adapter.LooperAdapter;
import com.finham.taobaocoupon.utils.Constants;
import com.finham.taobaocoupon.view.ICategoryPagerCallback;

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
    public ViewPager mViewPager;

    @BindView(R.id.home_pager_title) //重启就能找到id了= =
    public TextView mTextView;

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

    //继承于BaseFragment，那么那些变量也就都继承过来了。

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View view) {
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
        mViewPager.setOffscreenPageLimit();
    }

    @Override
    protected void initPresenter() {
        mCategoryPagerPresenter = CategoryPagerPresenterImpl.getInstance(); //通过接口获取实例，隐藏内部实现！妙
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

    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        mLooperAdapter.setData(contents);
    }

    @Override
    protected void release() {
        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.unregisterCallback(this);
        }
    }
}
