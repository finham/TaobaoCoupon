package com.finham.taobaocoupon.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finham.taobaocoupon.R;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/5/13
 * Time: 22:42
 */
public class TextFlowLayout extends ViewGroup {
    private List<String> mTextList;
    private float mItemHorizontalSpace = 10;
    private float mItemVerticalSpace = 10;

    //暴露出去给外面设置
    public float getItemHorizontalSpace() {
        return mItemHorizontalSpace;
    }

    public void setItemHorizontalSpace(float itemHorizontalSpace) {
        mItemHorizontalSpace = itemHorizontalSpace;
    }

    public float getItemVerticalSpace() {
        return mItemVerticalSpace;
    }

    public void setItemVerticalSpace(float itemVerticalSpace) {
        mItemVerticalSpace = itemVerticalSpace;
    }

    //三个构造方法统一一个入口，一般统一为有三个参数的构造方法
    public TextFlowLayout(Context context) {
        this(context, null);
    }

    public TextFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowTextStyle);
        mItemHorizontalSpace = array.getDimension(R.styleable.FlowTextStyle_horizontalSpace, 10);
        mItemVerticalSpace = array.getDimension(R.styleable.FlowTextStyle_verticalSpace, 10);
        array.recycle();
    }

    public void setTextList(List<String> textList) {
        this.mTextList = textList;
        //遍历
        for (String s : mTextList) {
            //TextView textView = new TextView(getContext());  //也可以使用xml来写。推荐现阶段还是使用XML来写
            //LayoutInflater.from(getContext()).inflate(R.layout.flow_text_view, this, true) 等价于下面两句，注意第三个参数是否黏贴到root中
            TextView item = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_text_view, this, false);
            item.setText("电脑");
            addView(item);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
