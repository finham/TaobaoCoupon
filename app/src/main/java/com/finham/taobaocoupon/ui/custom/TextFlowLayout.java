package com.finham.taobaocoupon.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.utils.LogUtils;

import java.util.ArrayList;
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
    //private List<View> mSingleLine;
    private List<List<View>> mAllLines = new ArrayList<>();
    private onFlowItemClickListener mListener;

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
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFlowItemClick(s);
                }
            });
            addView(item);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        List<View> mSingleLine = null;
        mAllLines.clear();
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        //先测量孩子
        //那么要先干啥呢？当然是遍历拿到子View啦！
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != VISIBLE) {
                //不需要进行测量，自然也就不会添加进List中。注意continue的用法
                continue;
            }
            //测量前
            LogUtils.d(TextFlowLayout.class, "before height-->" + childView.getMeasuredHeight()); //getHeight是onLayout确定的
            //进行测量
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //测量后
            LogUtils.d(TextFlowLayout.class, "after height-->" + childView.getMeasuredHeight());
            if (mSingleLine == null) {
                mSingleLine = new ArrayList<>();
                mSingleLine.add(childView);
                mAllLines.add(mSingleLine);
            } else {
                //判断单行还能不能继续添加得了
                //将所有已经添加进来的View的宽度相加 + 每个View之间的间距 + childView.getMeasuredWidth -->判断是否超出ViewGroup的宽度
                int totalWidth = 0;
                for (View view : mSingleLine) {
                    totalWidth += view.getMeasuredWidth();
                    //+= 是简写，a += 1就是a = a+1 | =+并不是简写，a =+ a直接对a的赋值，±符号代表的是正负（完全可以省略不写），即a =+ b其实就是a = b。
                }
                totalWidth += (mSingleLine.size() + 1) * mItemHorizontalSpace;
                totalWidth += childView.getMeasuredWidth(); //加的这个是准备新添加进来的
                //我们这里只需要size，不需要mode。应该是屏幕宽度，因为我们使用match_parent
                if (totalWidth <= selfWidth) {
                    //可以添加
                    mSingleLine.add(childView);
                } else {
                    //不能添加，新创建一行
                    mSingleLine = new ArrayList<>();
                    mSingleLine.add(childView);
                    mAllLines.add(mSingleLine);
                }
            }
        }
        //测量自己，去计算行数*行高度+间距
        int selfHeight = (int) (mAllLines.size() * getChildAt(0).getMeasuredHeight() + (mAllLines.size() + 1) * mItemVerticalSpace + 0.5f); //补0.5f为了四舍五入
        setMeasuredDimension(selfWidth, selfHeight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //演示一下该如何摆放！一步步拆解后自然就看得懂了。
        /**View childOne = getChildAt(0);
         childOne.layout(0, 0, childOne.getMeasuredWidth(), childOne.getMeasuredHeight()); //左、顶、右、底

         View childTwo = getChildAt(1);
         childTwo.layout(childOne.getRight() + (int) mItemHorizontalSpace, 0,
         childOne.getRight() + (int) mItemHorizontalSpace + childTwo.getMeasuredWidth(), childTwo.getMeasuredHeight());**/

        int topOffset = (int) mItemVerticalSpace;
        //通过上面两个的拆解，其实已经知道该如何摆放了。那这样一个个写肯定是不行的，必须借助for循环！
        for (List<View> line : mAllLines) {
            //测量的时候已经分好行了，那至于你要把分行拿到onLayout里来写也可以。
            int leftOffset = (int) mItemHorizontalSpace;
            for (View view : line) {
                //for双循环
                view.layout(leftOffset, topOffset, leftOffset + view.getMeasuredWidth(), topOffset + view.getMeasuredHeight());
                leftOffset += view.getMeasuredWidth() + mItemHorizontalSpace;
            }
            topOffset += line.get(0).getMeasuredHeight() + mItemVerticalSpace;
        }
    }

    public void setOnFlowItemClickListener(onFlowItemClickListener listener) {
        this.mListener = listener;
    }

    public interface onFlowItemClickListener {
        void onFlowItemClick(String text); //把点击的字传进去
    }
}
