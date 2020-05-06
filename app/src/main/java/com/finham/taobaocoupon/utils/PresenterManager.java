package com.finham.taobaocoupon.utils;

import com.finham.taobaocoupon.presenter.ICategoryPagerPresenter;
import com.finham.taobaocoupon.presenter.IHomePresenter;
import com.finham.taobaocoupon.presenter.IPreferentialPagePresenter;
import com.finham.taobaocoupon.presenter.ISelectedPagerPresenter;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.presenter.implement.CategoryPagerPresenterImpl;
import com.finham.taobaocoupon.presenter.implement.HomePresenterImpl;
import com.finham.taobaocoupon.presenter.implement.PreferentialPagePresenterImpl;
import com.finham.taobaocoupon.presenter.implement.SelectedPagerPresenterImpl;
import com.finham.taobaocoupon.presenter.implement.TicketPresenterImpl;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 22:25
 */
public class PresenterManager {
    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private IHomePresenter mHomePresenter;
    private ITicketPresenter mTicketPresenter;
    private ISelectedPagerPresenter mSelectedPagerPresenter;
    private IPreferentialPagePresenter mPreferentialPagePresenter;

    public ICategoryPagerPresenter getCategoryPagerPresenter() {
        return mCategoryPagerPresenter;
    }

    public IHomePresenter getHomePresenter() {
        return mHomePresenter;
    }


    public ITicketPresenter getTicketPresenter() {
        return mTicketPresenter;
    }


    public ISelectedPagerPresenter getSelectedPagerPresenter() {
        return mSelectedPagerPresenter;
    }


    public IPreferentialPagePresenter getPreferentialPagePresenter() {
        return mPreferentialPagePresenter;
    }

    //单例
    private static final PresenterManager ourInstance = new PresenterManager();

    public static PresenterManager getInstance() {
        return ourInstance;
    }

    private PresenterManager() {
        mCategoryPagerPresenter = new CategoryPagerPresenterImpl(); //通过接口获取实例，隐藏内部实现！妙
        mHomePresenter = new HomePresenterImpl();
        mTicketPresenter = new TicketPresenterImpl();
        mSelectedPagerPresenter = new SelectedPagerPresenterImpl();
        mPreferentialPagePresenter = new PreferentialPagePresenterImpl();
    }

}
