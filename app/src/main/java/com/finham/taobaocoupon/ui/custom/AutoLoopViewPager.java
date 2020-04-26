package com.finham.taobaocoupon.ui.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 16:40
 * 能够提供自动轮播的ViewPager
 */
public class AutoLoopViewPager extends ViewPager {
    public AutoLoopViewPager(@NonNull Context context) {
        super(context);
    }

    public AutoLoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isLooping = false;
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            //先拿到当前的位置
            int i = getCurrentItem();
            setCurrentItem(i++);
            if (isLooping) postDelayed(this, 3000);  //三秒后再执行这个动作~
            // 注意post并没有开启子线程哦！有start的才算开子线程。
        }
    };

    public void startLoop() {
        isLooping = true;
        //所有继承自View类的都有post()方法
        post(task);
    }

    public void stopLoop() {
        removeCallbacks(task);
        isLooping = false;
    }
}
