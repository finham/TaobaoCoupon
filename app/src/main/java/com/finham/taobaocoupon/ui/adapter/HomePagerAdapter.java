package com.finham.taobaocoupon.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.finham.taobaocoupon.model.domain.Category;
import com.finham.taobaocoupon.ui.fragment.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fin
 * Date: 2020/4/17
 * Time: 22:47
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Category.DataBean> mCaregoryList = new ArrayList<>();
    //behavior的值 BEHAVIOR_SET_USER_VISIBLE_HINT = 0;(已过时deprecated) BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); //这个behavior跟懒加载有很大联系。可具体参考整理的笔记
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mCaregoryList.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Category.DataBean bean = mCaregoryList.get(position);
        HomePagerFragment fragment = HomePagerFragment.newInstance(bean);
        return fragment;
    }

    @Override
    public int getCount() {
        return mCaregoryList.size();
    }

    public void setCategoryTitle(Category category) {
        mCaregoryList.clear();
        List<Category.DataBean> data = category.getData();
        mCaregoryList.addAll(data);
        notifyDataSetChanged();
    }
}
