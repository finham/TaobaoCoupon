package com.finham.taobaocoupon.utils;

import com.finham.taobaocoupon.presenter.ICategoryPagerPresenter;
import com.finham.taobaocoupon.presenter.IHomePresenter;
import com.finham.taobaocoupon.presenter.ITicketPresenter;
import com.finham.taobaocoupon.presenter.implement.CategoryPagerPresenterImpl;
import com.finham.taobaocoupon.presenter.implement.HomePresenterImpl;
import com.finham.taobaocoupon.presenter.implement.TicketPresenterImpl;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 22:25
 */
public class PresenterManager {
    public ICategoryPagerPresenter getCategoryPagerPresenter() {
        return mCategoryPagerPresenter;
    }

    private ICategoryPagerPresenter mCategoryPagerPresenter;

    public IHomePresenter getHomePresenter() {
        return mHomePresenter;
    }

    private IHomePresenter mHomePresenter;

    public ITicketPresenter getTicketPresenter() {
        return mTicketPresenter;
    }

    private ITicketPresenter mTicketPresenter;

    private static final PresenterManager ourInstance = new PresenterManager();

    public static PresenterManager getInstance() {
        return ourInstance;
    }

    private PresenterManager() {
        mCategoryPagerPresenter = new CategoryPagerPresenterImpl(); //通过接口获取实例，隐藏内部实现！妙
        mHomePresenter = new HomePresenterImpl();
        mTicketPresenter = new TicketPresenterImpl();
    }

}
