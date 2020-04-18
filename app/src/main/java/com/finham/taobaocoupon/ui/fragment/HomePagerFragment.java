package com.finham.taobaocoupon.ui.fragment;

import android.view.View;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;

/**
 * User: Fin
 * Date: 2020/4/18
 * Time: 14:32
 */
public class HomePagerFragment extends BaseFragment {
    //继承于BaseFragment，那么那些变量也就都继承过来了。

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View view) {
        changeState(State.SUCCESS);
    }
}
