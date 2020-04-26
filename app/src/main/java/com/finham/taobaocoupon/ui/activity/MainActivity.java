package com.finham.taobaocoupon.ui.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.base.BaseActivity;
import com.finham.taobaocoupon.base.BaseFragment;
import com.finham.taobaocoupon.ui.fragment.HomeFragment;
import com.finham.taobaocoupon.ui.fragment.PocketFragment;
import com.finham.taobaocoupon.ui.fragment.SearchFragment;
import com.finham.taobaocoupon.ui.fragment.SelectedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//    private static final String TAG = "MainActivity";
    @BindView(R.id.main_navigation_view) //黄油刀不能用来布局变量中，且不能为private或static，因为使用了反射
            BottomNavigationView bottom;

    private HomeFragment homeFragment;
    SelectedFragment selectedFragment;
    PocketFragment pocketFragment;
    SearchFragment searchFragment;

    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        selectedFragment = new SelectedFragment();
        pocketFragment = new PocketFragment();
        searchFragment = new SearchFragment();
    }

    @Override
    protected void initListener() {
        bottom.setOnNavigationItemSelectedListener(this);
        bottom.setSelectedItemId(R.id.home);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //等Fragment的事务使用熟练了，再去使用Navigation！
        //menu上的id不会智能提示，要自己手打完整
        //LogUtils使用方法：LogUtils.v(MainActivity.class, "首页");
        if (item.getItemId() == R.id.home) switchToFragment(homeFragment);
        else if (item.getItemId() == R.id.selected) switchToFragment(selectedFragment);
        else if (item.getItemId() == R.id.pocket) switchToFragment(pocketFragment);
        else if (item.getItemId() == R.id.search) switchToFragment(searchFragment);
        return true; //return true表示消费这个事件，图标才会真正跟着切换过去
    }

    //上一次显示的Fragment
    private BaseFragment mLastFragment = null;

    private void switchToFragment(BaseFragment target) {
        //修改为add+hide的方式来控制Fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!target.isAdded()) {
            transaction.add(R.id.main_page_container, target);
        } else {
            transaction.show(target);
        }
        if (mLastFragment != null) {
            transaction.hide(mLastFragment);
        }
        mLastFragment = target;
        //transaction.replace(R.id.main_page_container, target);
        transaction.commit();
    }

    //将一些公共的代码移到BaseActivity
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mBinder != null) mBinder.unbind();
//    }
}
