package com.finham.taobaocoupon.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.Category;
import com.finham.taobaocoupon.model.domain.HomePagerContent;
import com.finham.taobaocoupon.presenter.ICategoryPagerPresenter;
import com.finham.taobaocoupon.presenter.implement.CategoryPagerPresenterImpl;
import com.finham.taobaocoupon.utils.Constants;
import com.finham.taobaocoupon.view.ICategoryPagerCallback;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/4/18
 * Time: 14:32
 */
public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private int mMaterialId;

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
        changeState(State.SUCCESS);
    }

    @Override
    protected void initPresenter() {
        mCategoryPagerPresenter = CategoryPagerPresenterImpl.getInstance();
        mCategoryPagerPresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {  //loadData()是在loadView()之后的
        String title = getArguments().getString(Constants.HOME_PAGER_KEY_TITLE);
        mMaterialId = getArguments().getInt(Constants.HOME_PAGER_KEY_MATERIAL_ID);

        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.getContentByCategoryId(mMaterialId);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> contents, int categoryId) {
        if(mMaterialId != categoryId) return;
        changeState(State.SUCCESS);
    }

    @Override
    public void onContentLoading(int categoryId) {
        if(mMaterialId != categoryId) return;
        changeState(State.LOADING);
    }

    @Override
    public void onContentError(int categoryId) {
        if(mMaterialId != categoryId) return;
        //网络错误！
        changeState(State.ERROR);
    }

    @Override
    public void onContentEmpty(int categoryId) {
        if(mMaterialId != categoryId) return;
        changeState(State.EMPTY);
    }

    @Override
    public void onLoadMoreError(int categoryId) {

    }

    @Override
    public void onLoadMoreEmpty(int categoryId) {

    }

    @Override
    public void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents, int categoryId) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents, int categoryId) {

    }

    @Override
    protected void release() {
        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.unregisterCallback(this);
        }
    }
}
