package com.finham.taobaocoupon.ui.fragment;

import android.view.View;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 13:34
 */
public class SearchFragment extends BaseFragment {

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View view) {
        changeState(State.SUCCESS);
    }
}
