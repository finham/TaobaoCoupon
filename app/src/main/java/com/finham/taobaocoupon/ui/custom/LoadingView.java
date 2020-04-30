package com.finham.taobaocoupon.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.finham.taobaocoupon.R;

/**
 * User: Fin
 * Date: 2020/4/30
 * Time: 19:40
 */
public class LoadingView extends AppCompatImageView {
    private float mDegree = 0;
    private boolean isRotated = true;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isRotated = true;
        //Causes the Runnable to be added to the message queue. The runnable will be run on the UI thread.
        post(new Runnable() {
            @Override
            public void run() {
                mDegree += 10; //可以结合前面知识，把这个做成自定义属性
                if (mDegree >= 360) { //优化一下
                    mDegree = 0;
                }
                invalidate(); //证明…错误; 使站不住脚; 使无效; 使作废;
                //使用布尔值来控制是否要继续旋转
                if(getVisibility() == View.VISIBLE && isRotated) {
                    postDelayed(this, 10); //形成循环= = 所有View都有post()方法
                }else {
                    removeCallbacks(this);
                }
            }
        });
    }



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRotated = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(mDegree, getWidth() / 2, getHeight() / 2); //这句得放在super前面，不然不生效
        super.onDraw(canvas);
    }
}
