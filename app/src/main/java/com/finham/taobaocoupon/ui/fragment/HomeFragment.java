package com.finham.taobaocoupon.ui.fragment;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.Category;
import com.finham.taobaocoupon.presenter.IHomePresenter;
import com.finham.taobaocoupon.presenter.implement.HomePresenterImpl;
import com.finham.taobaocoupon.view.IHomeCallback;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 13:34
 */
public class HomeFragment extends BaseFragment implements IHomeCallback {
    //注意这边使用接口！为了不让别人看到实现！别人点击后就只能看到接口中定义的方法，而跳不到HomePresenterImpl中看具体实现！妙哉~
    private IHomePresenter homePresenter;
//    @Nullable
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
    protected void initPresenter() {
        //因为父类的initPresenter是个空方法，所以可以把super.initPresenter()给删掉。同理对如下的方法。就算父类有实现，但你不需要的话也可以删除
        //那如果有需要用到父类实现的代码，那就得保留
        //super.initPresenter();
        homePresenter = new HomePresenterImpl();
        homePresenter.registerCallback(this);
    }

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
        //加载的数据会从这里回来
    }

}
