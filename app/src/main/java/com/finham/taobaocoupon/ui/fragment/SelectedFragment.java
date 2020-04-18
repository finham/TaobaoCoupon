package com.finham.taobaocoupon.ui.fragment;

import android.view.View;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 13:34
 */
public class SelectedFragment extends BaseFragment {

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }

    //既然每个都要重写这个，那还不如放到BaseFragment里。
    //@Override
    //protected View loadRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //    return null;
    //}

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selected;
    }

    @Override
    protected void initView(View view) {
        changeState(State.SUCCESS);
    }
}
