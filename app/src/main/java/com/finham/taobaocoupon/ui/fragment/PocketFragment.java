package com.finham.taobaocoupon.ui.fragment;

import android.view.View;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 14:34
 */
public class PocketFragment extends BaseFragment {
    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_pocket;
    }

    @Override
    protected void initView(View view) {
        changeState(State.SUCCESS);
    }
}
