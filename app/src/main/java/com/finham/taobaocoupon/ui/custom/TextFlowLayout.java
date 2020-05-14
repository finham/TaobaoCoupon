package com.finham.taobaocoupon.ui.custom;

import android.content.Context;
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

    public TextFlowLayout(Context context) {
        super(context);
    }

    public TextFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextList(List<String> textList){
        this.mTextList = textList;
        //遍历
        for (String s : mTextList) {
            //TextView textView = new TextView(getContext());  //也可以使用xml来写。推荐现阶段还是使用XML来写
            //LayoutInflater.from(getContext()).inflate(R.layout.flow_text_view, this, true) 等价于下面两句，注意第三个参数是否黏贴到root中
            TextView item = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.flow_text_view, this, false);
            item.setText("电脑");
            addView(item);
        }
    }
    
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
