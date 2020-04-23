package com.finham.taobaocoupon.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.Category;
import com.finham.taobaocoupon.presenter.IHomePresenter;
import com.finham.taobaocoupon.presenter.implement.HomePresenterImpl;
import com.finham.taobaocoupon.ui.adapter.HomePagerAdapter;
import com.finham.taobaocoupon.view.IHomeCallback;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 13:34
 */
public class HomeFragment extends BaseFragment implements IHomeCallback {
    //注意这边使用接口！为了不让别人看到实现！别人点击后就只能看到接口中定义的方法，而跳不到HomePresenterImpl中看具体实现！妙哉~
    private IHomePresenter homePresenter;
    @BindView(R.id.home_indicator)
    public TabLayout mTabLayout; //要设置什么东西就回BaseFragment中去实现
    @BindView(R.id.home_pager)
    public ViewPager mViewPager;
    private HomePagerAdapter mAdapter;


//    @Nullable 这个提取出来统一放到BaseFragment中
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        return view;
//    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
//        super.initView(view);
        mTabLayout.setupWithViewPager(mViewPager);
        mAdapter = new HomePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

    }

    /**
     * BaseFragment的方法
     */
    @Override
    protected void initPresenter() {
        //因为父类的initPresenter是个空方法，所以可以把super.initPresenter()给删掉。同理对如下的方法。就算父类有实现，但你不需要的话也可以删除
        //那如果有需要用到父类实现的代码，那就得保留
        //super.initPresenter();
        homePresenter = new HomePresenterImpl();
        homePresenter.registerViewCallback(this);
    }

    /**
     * BaseFragment的方法
     */
    @Override
    protected void loadData() {
        homePresenter.getCategories();
    }

    @Override
    protected void release() {
        //取消回调
        if (homePresenter != null)
            homePresenter.unregisterCallback(this);
    }

    @Override
    public void onCategoriesLoaded(Category category) {
        changeState(State.SUCCESS);
        //加载的数据会从这里回来
        if (mAdapter != null)
            mAdapter.setCategoryTitle(category);
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
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_home_fragment_layout, container, false);
    }

    /*选择在HomeFragment中重写该方法*/
    @Override
    protected void onRetryClick() {
        //重新加载category
        if (homePresenter != null) {
            homePresenter.getCategories();
        }
    }
}
