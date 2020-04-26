package com.finham.taobaocoupon.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 20:16
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mBinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        mBinder = ButterKnife.bind(this);
        initView();
        initListener();
    }

    /**
     * 需要的时候自己复写
     */
    protected void initListener() {
    }

    protected abstract void initView();

    protected abstract int getLayoutResourceId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinder != null) mBinder.unbind();
    }
}
