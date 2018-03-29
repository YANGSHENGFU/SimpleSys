package com.sys.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import com.sys.R;

/**
 * Created by LY on 2018/3/25.
 */

public class HomeActivity extends FragmentActivity {

    private ChatFragment mFragment = new ChatFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        findViewById() ;
        getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout,mFragment).commitAllowingStateLoss();
    }

    /**
     *
     */
    private void findViewById(){
    }

}
