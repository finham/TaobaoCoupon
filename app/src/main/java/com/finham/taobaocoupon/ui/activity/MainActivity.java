package com.finham.taobaocoupon.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottom = findViewById(R.id.main_navigation_view);
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

        } else if (item.getItemId() == R.id.selected) {

        } else if (item.getItemId() == R.id.pocket) {

        } else if (item.getItemId() == R.id.search) {

        }
        return true; //return true表示消费这个事件，图标才会真正跟着切换过去
    }
}
