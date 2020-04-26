package com.finham.taobaocoupon.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * User: Fin
 * Date: 2020/4/25
 * Time: 22:14
 */
public class TaobaoNestedScrollView extends NestedScrollView {
    private int height = 100;
    private int originScroll = 0;

    public TaobaoNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public TaobaoNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TaobaoNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
//    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * onNestedPreScroll is called when a nested scrolling child invokes View.dispatchNestedPreScroll(int, int, int[], int[]).
     * The implementation should report how any pixels of the scroll reported by dx, dy were consumed in the consumed array.
     * Index 0 corresponds to dx and index 1 corresponds to dy.
     * This parameter will never be null. Initial values for consumed[0] and consumed[1] will always be 0.
     *
     * @param target
     * @param dx       滑动的差值
     * @param dy       也是一样的
     * @param consumed
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(target, dx, dy, consumed, type);
        if(originScroll<height) {
            scrollBy(dx, dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }
        //加了上面三句后就可以滑动了，但是当滑动到完全隐藏轮播图和title时就无法滑动了！
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //只要你NestedScrollView一有移动，那么这个方法就会调用且有值的变化
        this.originScroll = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
