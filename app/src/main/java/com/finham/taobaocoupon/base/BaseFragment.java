package com.finham.taobaocoupon.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 14:11
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder mBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = loadRootView(inflater, container, savedInstanceState);
        mBinder = ButterKnife.bind(this, view);
        initView(view); //把View传递过去，用得上就用，用不上也没事
        initPresenter();
        loadData();
        return view;
    }

    protected void initView(View view) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //不为空记得解绑
        if (mBinder != null) mBinder.unbind();
        release();
    }

    protected void release() {
        //释放资源
    }

    protected void initPresenter() {
        //创建Presenter
    }

    protected void loadData() {
        //加载数据
    }

    //甚至这个方法也可以简写不要，不过还是跟着老师来吧。
    private View loadRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = getRootViewResId();
        return inflater.inflate(resId, container, false);
    }

    protected abstract int getRootViewResId();
}
