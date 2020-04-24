package com.finham.taobaocoupon.utils;

import android.content.Context;

/**
 * User: Fin
 * Date: 2020/4/24
 * Time: 15:57
 */
public class DensityUtils {
    //dp转px
    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
