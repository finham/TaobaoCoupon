package com.finham.taobaocoupon.ui.fragment;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.model.domain.SelectedCategory;
import com.finham.taobaocoupon.model.domain.SelectedContent;
import com.finham.taobaocoupon.presenter.ISelectedPagerPresenter;
import com.finham.taobaocoupon.ui.adapter.SelectedCategoryAdapter;
import com.finham.taobaocoupon.utils.PresenterManager;
import com.finham.taobaocoupon.view.ISelectedPagerCallback;

import java.util.List;

import butterknife.BindView;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 13:34
 */
public class SelectedFragment extends BaseFragment implements ISelectedPagerCallback {
    @BindView(R.id.left_category_list)
    public RecyclerView mLeft;
    @BindView(R.id.right_content_list)
    public RecyclerView mRight;

    ISelectedPagerPresenter mSelectedPagerPresenter;
    private SelectedCategoryAdapter mAdapter;

    @Override
    protected void initPresenter() {
        mSelectedPagerPresenter = PresenterManager.getInstance().getSelectedPagerPresenter();
        mSelectedPagerPresenter.registerViewCallback(this); //让当前类实现该接口，也就相当于注册了回调！妙哉
    }

    @Override
    protected void release() {
        if (mSelectedPagerPresenter != null) {
            mSelectedPagerPresenter.unregisterCallback(this);
        }
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selected;
    }

    @Override
    protected void initView(View view) {
        changeState(State.SUCCESS);
        mLeft.setLayoutManager(new LinearLayoutManager(requireContext()));
        mAdapter = new SelectedCategoryAdapter();
        mLeft.setAdapter(mAdapter);
    }

    @Override
    public void onCategoryLoaded(SelectedCategory category) {
        mAdapter.setData(category);
        //分类的数据会从这个方法传回来！分类的操作要在这里做
        //根据当前的分类，然后再去拿内容数据
        List<SelectedCategory.DataBean> contents = category.getData();
        mSelectedPagerPresenter.getContentByCategory(contents.get(0));
    }

    @Override
    public void onContentLoaded(SelectedContent content) {
        //上面写那两句后来这边看看数据是否正确回来
        Log.d("SelectedFragment", "onContentLoaded-->" + content.getData().getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item().get(0).getTitle());//记得去bean类中复写toString()。
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}
