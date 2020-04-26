package com.finham.taobaocoupon.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.finham.taobaocoupon.R;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 16:40
 * 能够提供自动轮播的ViewPager
 */
public class AutoLoopViewPager extends ViewPager {
    public static final int DEFAULT_INTERVAL = 3000; //int 或long看你心情吧
    private int mInterval = DEFAULT_INTERVAL;

    public AutoLoopViewPager(@NonNull Context context) {
        super(context, null); //统一一个入口
    }

    public AutoLoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //在这里读取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLoopViewPager);
        //获取属性，注意第一个参数的命名规则
        mInterval = typedArray.getInt(R.styleable.AutoLoopViewPager_interval, DEFAULT_INTERVAL);
        //回收资源
        typedArray.recycle();
    }

    private boolean isLooping = false;
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            //先拿到当前的位置
            int i = getCurrentItem();
            i++;
            setCurrentItem(i); //这边不能直接i++传进来啊
            if (isLooping) postDelayed(this, mInterval);  //三秒后再执行这个动作~
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

    /**
     * 注释要好好写，因为别人可能会进来看。
     * 单位为毫秒
     *
     * @param newInterval
     */
    public void setInterval(int newInterval) {
        mInterval = newInterval;
    }
}
