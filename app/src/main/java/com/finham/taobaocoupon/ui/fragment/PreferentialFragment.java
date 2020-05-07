package com.finham.taobaocoupon.ui.fragment;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.PreferentialContent;
import com.finham.taobaocoupon.presenter.IPreferentialPagePresenter;
import com.finham.taobaocoupon.ui.adapter.PreferentialAdapter;
import com.finham.taobaocoupon.utils.PresenterManager;
import com.finham.taobaocoupon.view.IPreferentialPageCallback;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;

import butterknife.BindView;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 14:34
 */
public class PreferentialFragment extends BaseFragment implements IPreferentialPageCallback {

    IPreferentialPagePresenter mPreferentialPagePresenter;
    @BindView(R.id.preferential_content_list)
    public RecyclerView mRecyclerView;
    private PreferentialAdapter mAdapter;

    @Override
    protected void initView(View view) {
        mAdapter = new PreferentialAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = DensityUtil.dp2px(requireContext(),2.5f);
                outRect.bottom = DensityUtil.dp2px(requireContext(),2.5f);
                outRect.left = DensityUtil.dp2px(requireContext(),2.5f);
                outRect.right = DensityUtil.dp2px(requireContext(),2.5f);
            }
        });
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initPresenter() {
        mPreferentialPagePresenter = PresenterManager.getInstance().getPreferentialPagePresenter();
        mPreferentialPagePresenter.registerViewCallback(this);
        mPreferentialPagePresenter.getPreferentialContent(); //拿到数据后就直接跳转到回调方法 onContentLoaded()
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_preferential;
    }

    @Override
    protected void release() {
        if (mPreferentialPagePresenter != null) {
            mPreferentialPagePresenter.unregisterCallback(this);
        }
    }

    @Override
    public void onContentLoaded(PreferentialContent content) {
        changeState(State.SUCCESS);
        //数据从这个方法回来了！在此更新UI
        mAdapter.setData(content);
    }

    @Override
    public void onLoadMore(PreferentialContent moreContent) {

    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onLoadMoreEmpty() {

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
}
