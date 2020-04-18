package com.finham.taobaocoupon.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finham.taobaocoupon.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 14:11
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder mBinder;
    private FrameLayout mFrameLayout;
    private View mSuccessView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    public enum State {
        NONE, LOADING, SUCCESS, ERROR, EMPTY
    }

    private State currentState = State.NONE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base_fragment_layout, container, false);
        mFrameLayout = rootView.findViewById(R.id.base_container);
        loadState(inflater, container);

        mBinder = ButterKnife.bind(this, rootView);
        initView(rootView); //把View传递过去，用得上就用，用不上也没事
        initPresenter();
        loadData();
        return rootView;
    }

    /**
     * 加载对应状态的View
     *
     * @param inflater
     * @param container
     */
    private void loadState(LayoutInflater inflater, ViewGroup container) {
        //添加success
        mSuccessView = loadSuccessView(inflater, container); //addView这个方法是第一次使用。
        mFrameLayout.addView(mSuccessView);
        //添加loading
        mLoadingView = loadLoadingView(inflater, container);
        mFrameLayout.addView(mLoadingView);
        //添加error
        mErrorView = loadErrorView(inflater, container);
        mFrameLayout.addView(mErrorView);
        //添加empty
        mEmptyView = loadEmptyView(inflater, container);
        mFrameLayout.addView(mEmptyView);
        changeState(State.NONE);

    }

    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }

    /**
     * 子类通过该方法来切换不同状态对应的页面
     *
     * @param state
     */
    public void changeState(State state) {
        this.currentState = state;

        if (currentState == State.SUCCESS)
            mSuccessView.setVisibility(View.VISIBLE);
        else
            mSuccessView.setVisibility(View.GONE);

        if (currentState == State.LOADING)
            mLoadingView.setVisibility(View.VISIBLE);
        else
            mLoadingView.setVisibility(View.GONE);

        mErrorView.setVisibility(currentState == State.ERROR ? View.INVISIBLE : View.GONE);
        mEmptyView.setVisibility(currentState == State.EMPTY ? View.INVISIBLE : View.GONE);
    }

    /**
     * 加载页面
     * 子类可选择重写
     *
     * @param inflater
     * @param container
     * @return
     */
    protected View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading, container, false);
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
    protected View loadSuccessView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        int resId = getRootViewResId();
        return inflater.inflate(resId, container, false);
    }

    protected abstract int getRootViewResId();
}
