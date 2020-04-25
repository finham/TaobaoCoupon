package com.finham.taobaocoupon.utils;

import android.widget.Toast;

import com.finham.taobaocoupon.base.BaseApplication;

/**
 * User: Fin
 * Date: 2020/4/25
 * Time: 14:35
 */
public class ToastUtils {

    //这种写法是网上大多数人用的，不过似乎有的时候效果不好= =具体不清楚，因为我也没用过。
    private static Toast sToast;

    public static void showToast(String tip){
        if(sToast == null) {
            sToast = Toast.makeText(BaseApplication.getContext(), "", Toast.LENGTH_SHORT);//去掉小米自带的应用名称
            sToast.setText(tip);
        }else{
            sToast.setText(tip);
        }
        sToast.show();
    }
}
