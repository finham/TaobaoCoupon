package com.finham.taobaocoupon.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.finham.taobaocoupon.model.domain.HomePagerContent;
import com.finham.taobaocoupon.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fin
 * Date: 2020/4/23
 * Time: 23:29
 */

/**
 * Base class providing the adapter to populate pages inside of a ViewPager.
 * You will most likely want to use a more specific implementation of this,
 * such as androidx.fragment.app.FragmentPagerAdapter or androidx.fragment.app.FragmentStatePagerAdapter.
 * When you implement a PagerAdapter, you must override the following methods at minimum: 至少要重写这四个方法
 * <p>
 * instantiateItem(ViewGroup, int)
 * destroyItem(ViewGroup, int, Object)
 * getCount()
 * isViewFromObject(View, Object)
 */
public class LooperAdapter extends PagerAdapter {

    List<HomePagerContent.DataBean> mData = new ArrayList<>();
    private onLooperClickListener mOnLooperClickListener;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //返回Integer.MAX_VALUE会造成下标越界，需要处理
        int realPosition = position % mData.size(); //求余数
        //其实所有布局代码(xml)都会映射为Java类，这里演示使用Java代码来布局
        ImageView imageView = new ImageView(container.getContext());
        //将ImageView扩大宽度至和VP一样宽
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        //设置拉伸形式
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String url = mData.get(realPosition).getPict_url();
        int width = container.getMeasuredWidth();
        int height = container.getMeasuredHeight();
        int coverSize = (width > height ? width : height) / 2;

        Glide.with(container.getContext()).load(UrlUtils.getCoverPath(url, coverSize)).into(imageView);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnLooperClickListener != null) {
                    mOnLooperClickListener.onLooperClick(mData.get(realPosition));
                }
            }
        });
        return imageView;
    }

    public int getDataSize() {
        return mData.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object; //判断内存地址
    }

    public void setData(List<HomePagerContent.DataBean> contents) {
        //每次使用这种来更新会不会消耗过大= =
        mData.clear();
        mData.addAll(contents);
        notifyDataSetChanged();
    }

    public void setOnLooperClickListener(onLooperClickListener listener){
        this.mOnLooperClickListener = listener;
    }

    public interface onLooperClickListener{
        void onLooperClick(HomePagerContent.DataBean item);
    }
}
