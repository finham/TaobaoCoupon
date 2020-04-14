package com.finham.taobaocoupon.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.ui.fragment.HomeFragment;
import com.finham.taobaocoupon.utils.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.main_page_container)
    BottomNavigationView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //bottom = findViewById(R.id.main_navigation_view); //黄油刀不能用来布局变量中，且不能为private或static，因为使用了反射
        bottom.setOnNavigationItemSelectedListener(this);

        //可以使用Navigation来做！
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_page_container, homeFragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //等Fragment的事务使用熟练了，再去使用Navigation！
//        Log.d(TAG, "title -->" + item.getTitle() + "id -->" + item.getItemId());
        if (item.getItemId() == R.id.home) {  //menu上的id不会智能提示，要自己手打完整
            LogUtils.v(MainActivity.class,"首页");
        } else if (item.getItemId() == R.id.selected) {
            LogUtils.v(MainActivity.class,"精选");
        } else if (item.getItemId() == R.id.pocket) {
            LogUtils.v(MainActivity.class,"特惠");
        } else if (item.getItemId() == R.id.search) {
            LogUtils.v(MainActivity.class,"搜索");
        }
        return true; //return true表示消费这个事件，图标才会真正跟着切换过去
    }
}
