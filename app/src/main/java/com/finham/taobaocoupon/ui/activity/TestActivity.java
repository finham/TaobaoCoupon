package com.finham.taobaocoupon.ui.activity;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finham.taobaocoupon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: Fin
 * Date: 2020/4/14
 * Time: 21:27
 */
public class TestActivity extends AppCompatActivity {
    @BindView(R.id.radio_group)
    public RadioGroup mGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //拿到一个陌生的方法时，肯定是用log或Toast来弄明白参数的含义
                switch (i) {
                    case R.id.test_home:
                        break;
                    case R.id.test_selected:
                        break;
                    case R.id.test_pocket:
                        break;
                    case R.id.test_search:
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
